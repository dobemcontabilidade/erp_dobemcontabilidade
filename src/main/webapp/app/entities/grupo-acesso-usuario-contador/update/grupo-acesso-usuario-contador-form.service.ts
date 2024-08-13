import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IGrupoAcessoUsuarioContador, NewGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGrupoAcessoUsuarioContador for edit and NewGrupoAcessoUsuarioContadorFormGroupInput for create.
 */
type GrupoAcessoUsuarioContadorFormGroupInput = IGrupoAcessoUsuarioContador | PartialWithRequiredKeyOf<NewGrupoAcessoUsuarioContador>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IGrupoAcessoUsuarioContador | NewGrupoAcessoUsuarioContador> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

type GrupoAcessoUsuarioContadorFormRawValue = FormValueOf<IGrupoAcessoUsuarioContador>;

type NewGrupoAcessoUsuarioContadorFormRawValue = FormValueOf<NewGrupoAcessoUsuarioContador>;

type GrupoAcessoUsuarioContadorFormDefaults = Pick<NewGrupoAcessoUsuarioContador, 'id' | 'dataExpiracao' | 'ilimitado' | 'desabilitar'>;

type GrupoAcessoUsuarioContadorFormGroupContent = {
  id: FormControl<GrupoAcessoUsuarioContadorFormRawValue['id'] | NewGrupoAcessoUsuarioContador['id']>;
  dataExpiracao: FormControl<GrupoAcessoUsuarioContadorFormRawValue['dataExpiracao']>;
  ilimitado: FormControl<GrupoAcessoUsuarioContadorFormRawValue['ilimitado']>;
  desabilitar: FormControl<GrupoAcessoUsuarioContadorFormRawValue['desabilitar']>;
  usuarioContador: FormControl<GrupoAcessoUsuarioContadorFormRawValue['usuarioContador']>;
  grupoAcessoPadrao: FormControl<GrupoAcessoUsuarioContadorFormRawValue['grupoAcessoPadrao']>;
};

export type GrupoAcessoUsuarioContadorFormGroup = FormGroup<GrupoAcessoUsuarioContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoUsuarioContadorFormService {
  createGrupoAcessoUsuarioContadorFormGroup(
    grupoAcessoUsuarioContador: GrupoAcessoUsuarioContadorFormGroupInput = { id: null },
  ): GrupoAcessoUsuarioContadorFormGroup {
    const grupoAcessoUsuarioContadorRawValue = this.convertGrupoAcessoUsuarioContadorToGrupoAcessoUsuarioContadorRawValue({
      ...this.getFormDefaults(),
      ...grupoAcessoUsuarioContador,
    });
    return new FormGroup<GrupoAcessoUsuarioContadorFormGroupContent>({
      id: new FormControl(
        { value: grupoAcessoUsuarioContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataExpiracao: new FormControl(grupoAcessoUsuarioContadorRawValue.dataExpiracao),
      ilimitado: new FormControl(grupoAcessoUsuarioContadorRawValue.ilimitado),
      desabilitar: new FormControl(grupoAcessoUsuarioContadorRawValue.desabilitar),
      usuarioContador: new FormControl(grupoAcessoUsuarioContadorRawValue.usuarioContador, {
        validators: [Validators.required],
      }),
      grupoAcessoPadrao: new FormControl(grupoAcessoUsuarioContadorRawValue.grupoAcessoPadrao, {
        validators: [Validators.required],
      }),
    });
  }

  getGrupoAcessoUsuarioContador(form: GrupoAcessoUsuarioContadorFormGroup): IGrupoAcessoUsuarioContador | NewGrupoAcessoUsuarioContador {
    return this.convertGrupoAcessoUsuarioContadorRawValueToGrupoAcessoUsuarioContador(
      form.getRawValue() as GrupoAcessoUsuarioContadorFormRawValue | NewGrupoAcessoUsuarioContadorFormRawValue,
    );
  }

  resetForm(form: GrupoAcessoUsuarioContadorFormGroup, grupoAcessoUsuarioContador: GrupoAcessoUsuarioContadorFormGroupInput): void {
    const grupoAcessoUsuarioContadorRawValue = this.convertGrupoAcessoUsuarioContadorToGrupoAcessoUsuarioContadorRawValue({
      ...this.getFormDefaults(),
      ...grupoAcessoUsuarioContador,
    });
    form.reset(
      {
        ...grupoAcessoUsuarioContadorRawValue,
        id: { value: grupoAcessoUsuarioContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GrupoAcessoUsuarioContadorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataExpiracao: currentTime,
      ilimitado: false,
      desabilitar: false,
    };
  }

  private convertGrupoAcessoUsuarioContadorRawValueToGrupoAcessoUsuarioContador(
    rawGrupoAcessoUsuarioContador: GrupoAcessoUsuarioContadorFormRawValue | NewGrupoAcessoUsuarioContadorFormRawValue,
  ): IGrupoAcessoUsuarioContador | NewGrupoAcessoUsuarioContador {
    return {
      ...rawGrupoAcessoUsuarioContador,
      dataExpiracao: dayjs(rawGrupoAcessoUsuarioContador.dataExpiracao, DATE_TIME_FORMAT),
    };
  }

  private convertGrupoAcessoUsuarioContadorToGrupoAcessoUsuarioContadorRawValue(
    grupoAcessoUsuarioContador:
      | IGrupoAcessoUsuarioContador
      | (Partial<NewGrupoAcessoUsuarioContador> & GrupoAcessoUsuarioContadorFormDefaults),
  ): GrupoAcessoUsuarioContadorFormRawValue | PartialWithRequiredKeyOf<NewGrupoAcessoUsuarioContadorFormRawValue> {
    return {
      ...grupoAcessoUsuarioContador,
      dataExpiracao: grupoAcessoUsuarioContador.dataExpiracao
        ? grupoAcessoUsuarioContador.dataExpiracao.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
