import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPerfilAcessoUsuario, NewPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPerfilAcessoUsuario for edit and NewPerfilAcessoUsuarioFormGroupInput for create.
 */
type PerfilAcessoUsuarioFormGroupInput = IPerfilAcessoUsuario | PartialWithRequiredKeyOf<NewPerfilAcessoUsuario>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPerfilAcessoUsuario | NewPerfilAcessoUsuario> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

type PerfilAcessoUsuarioFormRawValue = FormValueOf<IPerfilAcessoUsuario>;

type NewPerfilAcessoUsuarioFormRawValue = FormValueOf<NewPerfilAcessoUsuario>;

type PerfilAcessoUsuarioFormDefaults = Pick<NewPerfilAcessoUsuario, 'id' | 'autorizado' | 'dataExpiracao'>;

type PerfilAcessoUsuarioFormGroupContent = {
  id: FormControl<PerfilAcessoUsuarioFormRawValue['id'] | NewPerfilAcessoUsuario['id']>;
  nome: FormControl<PerfilAcessoUsuarioFormRawValue['nome']>;
  autorizado: FormControl<PerfilAcessoUsuarioFormRawValue['autorizado']>;
  dataExpiracao: FormControl<PerfilAcessoUsuarioFormRawValue['dataExpiracao']>;
};

export type PerfilAcessoUsuarioFormGroup = FormGroup<PerfilAcessoUsuarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PerfilAcessoUsuarioFormService {
  createPerfilAcessoUsuarioFormGroup(perfilAcessoUsuario: PerfilAcessoUsuarioFormGroupInput = { id: null }): PerfilAcessoUsuarioFormGroup {
    const perfilAcessoUsuarioRawValue = this.convertPerfilAcessoUsuarioToPerfilAcessoUsuarioRawValue({
      ...this.getFormDefaults(),
      ...perfilAcessoUsuario,
    });
    return new FormGroup<PerfilAcessoUsuarioFormGroupContent>({
      id: new FormControl(
        { value: perfilAcessoUsuarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(perfilAcessoUsuarioRawValue.nome),
      autorizado: new FormControl(perfilAcessoUsuarioRawValue.autorizado),
      dataExpiracao: new FormControl(perfilAcessoUsuarioRawValue.dataExpiracao),
    });
  }

  getPerfilAcessoUsuario(form: PerfilAcessoUsuarioFormGroup): IPerfilAcessoUsuario | NewPerfilAcessoUsuario {
    return this.convertPerfilAcessoUsuarioRawValueToPerfilAcessoUsuario(
      form.getRawValue() as PerfilAcessoUsuarioFormRawValue | NewPerfilAcessoUsuarioFormRawValue,
    );
  }

  resetForm(form: PerfilAcessoUsuarioFormGroup, perfilAcessoUsuario: PerfilAcessoUsuarioFormGroupInput): void {
    const perfilAcessoUsuarioRawValue = this.convertPerfilAcessoUsuarioToPerfilAcessoUsuarioRawValue({
      ...this.getFormDefaults(),
      ...perfilAcessoUsuario,
    });
    form.reset(
      {
        ...perfilAcessoUsuarioRawValue,
        id: { value: perfilAcessoUsuarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PerfilAcessoUsuarioFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      autorizado: false,
      dataExpiracao: currentTime,
    };
  }

  private convertPerfilAcessoUsuarioRawValueToPerfilAcessoUsuario(
    rawPerfilAcessoUsuario: PerfilAcessoUsuarioFormRawValue | NewPerfilAcessoUsuarioFormRawValue,
  ): IPerfilAcessoUsuario | NewPerfilAcessoUsuario {
    return {
      ...rawPerfilAcessoUsuario,
      dataExpiracao: dayjs(rawPerfilAcessoUsuario.dataExpiracao, DATE_TIME_FORMAT),
    };
  }

  private convertPerfilAcessoUsuarioToPerfilAcessoUsuarioRawValue(
    perfilAcessoUsuario: IPerfilAcessoUsuario | (Partial<NewPerfilAcessoUsuario> & PerfilAcessoUsuarioFormDefaults),
  ): PerfilAcessoUsuarioFormRawValue | PartialWithRequiredKeyOf<NewPerfilAcessoUsuarioFormRawValue> {
    return {
      ...perfilAcessoUsuario,
      dataExpiracao: perfilAcessoUsuario.dataExpiracao ? perfilAcessoUsuario.dataExpiracao.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
