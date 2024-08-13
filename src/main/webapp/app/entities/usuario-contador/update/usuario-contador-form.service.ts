import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUsuarioContador, NewUsuarioContador } from '../usuario-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUsuarioContador for edit and NewUsuarioContadorFormGroupInput for create.
 */
type UsuarioContadorFormGroupInput = IUsuarioContador | PartialWithRequiredKeyOf<NewUsuarioContador>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IUsuarioContador | NewUsuarioContador> = Omit<T, 'dataHoraAtivacao' | 'dataLimiteAcesso'> & {
  dataHoraAtivacao?: string | null;
  dataLimiteAcesso?: string | null;
};

type UsuarioContadorFormRawValue = FormValueOf<IUsuarioContador>;

type NewUsuarioContadorFormRawValue = FormValueOf<NewUsuarioContador>;

type UsuarioContadorFormDefaults = Pick<NewUsuarioContador, 'id' | 'ativo' | 'dataHoraAtivacao' | 'dataLimiteAcesso'>;

type UsuarioContadorFormGroupContent = {
  id: FormControl<UsuarioContadorFormRawValue['id'] | NewUsuarioContador['id']>;
  email: FormControl<UsuarioContadorFormRawValue['email']>;
  senha: FormControl<UsuarioContadorFormRawValue['senha']>;
  token: FormControl<UsuarioContadorFormRawValue['token']>;
  ativo: FormControl<UsuarioContadorFormRawValue['ativo']>;
  dataHoraAtivacao: FormControl<UsuarioContadorFormRawValue['dataHoraAtivacao']>;
  dataLimiteAcesso: FormControl<UsuarioContadorFormRawValue['dataLimiteAcesso']>;
  situacao: FormControl<UsuarioContadorFormRawValue['situacao']>;
};

export type UsuarioContadorFormGroup = FormGroup<UsuarioContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UsuarioContadorFormService {
  createUsuarioContadorFormGroup(usuarioContador: UsuarioContadorFormGroupInput = { id: null }): UsuarioContadorFormGroup {
    const usuarioContadorRawValue = this.convertUsuarioContadorToUsuarioContadorRawValue({
      ...this.getFormDefaults(),
      ...usuarioContador,
    });
    return new FormGroup<UsuarioContadorFormGroupContent>({
      id: new FormControl(
        { value: usuarioContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      email: new FormControl(usuarioContadorRawValue.email, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      senha: new FormControl(usuarioContadorRawValue.senha, {
        validators: [Validators.required],
      }),
      token: new FormControl(usuarioContadorRawValue.token),
      ativo: new FormControl(usuarioContadorRawValue.ativo),
      dataHoraAtivacao: new FormControl(usuarioContadorRawValue.dataHoraAtivacao),
      dataLimiteAcesso: new FormControl(usuarioContadorRawValue.dataLimiteAcesso),
      situacao: new FormControl(usuarioContadorRawValue.situacao),
    });
  }

  getUsuarioContador(form: UsuarioContadorFormGroup): IUsuarioContador | NewUsuarioContador {
    return this.convertUsuarioContadorRawValueToUsuarioContador(
      form.getRawValue() as UsuarioContadorFormRawValue | NewUsuarioContadorFormRawValue,
    );
  }

  resetForm(form: UsuarioContadorFormGroup, usuarioContador: UsuarioContadorFormGroupInput): void {
    const usuarioContadorRawValue = this.convertUsuarioContadorToUsuarioContadorRawValue({ ...this.getFormDefaults(), ...usuarioContador });
    form.reset(
      {
        ...usuarioContadorRawValue,
        id: { value: usuarioContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UsuarioContadorFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ativo: false,
      dataHoraAtivacao: currentTime,
      dataLimiteAcesso: currentTime,
    };
  }

  private convertUsuarioContadorRawValueToUsuarioContador(
    rawUsuarioContador: UsuarioContadorFormRawValue | NewUsuarioContadorFormRawValue,
  ): IUsuarioContador | NewUsuarioContador {
    return {
      ...rawUsuarioContador,
      dataHoraAtivacao: dayjs(rawUsuarioContador.dataHoraAtivacao, DATE_TIME_FORMAT),
      dataLimiteAcesso: dayjs(rawUsuarioContador.dataLimiteAcesso, DATE_TIME_FORMAT),
    };
  }

  private convertUsuarioContadorToUsuarioContadorRawValue(
    usuarioContador: IUsuarioContador | (Partial<NewUsuarioContador> & UsuarioContadorFormDefaults),
  ): UsuarioContadorFormRawValue | PartialWithRequiredKeyOf<NewUsuarioContadorFormRawValue> {
    return {
      ...usuarioContador,
      dataHoraAtivacao: usuarioContador.dataHoraAtivacao ? usuarioContador.dataHoraAtivacao.format(DATE_TIME_FORMAT) : undefined,
      dataLimiteAcesso: usuarioContador.dataLimiteAcesso ? usuarioContador.dataLimiteAcesso.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
