import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUsuarioGestao, NewUsuarioGestao } from '../usuario-gestao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUsuarioGestao for edit and NewUsuarioGestaoFormGroupInput for create.
 */
type UsuarioGestaoFormGroupInput = IUsuarioGestao | PartialWithRequiredKeyOf<NewUsuarioGestao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IUsuarioGestao | NewUsuarioGestao> = Omit<T, 'dataHoraAtivacao' | 'dataLimiteAcesso'> & {
  dataHoraAtivacao?: string | null;
  dataLimiteAcesso?: string | null;
};

type UsuarioGestaoFormRawValue = FormValueOf<IUsuarioGestao>;

type NewUsuarioGestaoFormRawValue = FormValueOf<NewUsuarioGestao>;

type UsuarioGestaoFormDefaults = Pick<NewUsuarioGestao, 'id' | 'ativo' | 'dataHoraAtivacao' | 'dataLimiteAcesso'>;

type UsuarioGestaoFormGroupContent = {
  id: FormControl<UsuarioGestaoFormRawValue['id'] | NewUsuarioGestao['id']>;
  email: FormControl<UsuarioGestaoFormRawValue['email']>;
  senha: FormControl<UsuarioGestaoFormRawValue['senha']>;
  token: FormControl<UsuarioGestaoFormRawValue['token']>;
  ativo: FormControl<UsuarioGestaoFormRawValue['ativo']>;
  dataHoraAtivacao: FormControl<UsuarioGestaoFormRawValue['dataHoraAtivacao']>;
  dataLimiteAcesso: FormControl<UsuarioGestaoFormRawValue['dataLimiteAcesso']>;
  situacaoUsuarioGestao: FormControl<UsuarioGestaoFormRawValue['situacaoUsuarioGestao']>;
  administrador: FormControl<UsuarioGestaoFormRawValue['administrador']>;
};

export type UsuarioGestaoFormGroup = FormGroup<UsuarioGestaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UsuarioGestaoFormService {
  createUsuarioGestaoFormGroup(usuarioGestao: UsuarioGestaoFormGroupInput = { id: null }): UsuarioGestaoFormGroup {
    const usuarioGestaoRawValue = this.convertUsuarioGestaoToUsuarioGestaoRawValue({
      ...this.getFormDefaults(),
      ...usuarioGestao,
    });
    return new FormGroup<UsuarioGestaoFormGroupContent>({
      id: new FormControl(
        { value: usuarioGestaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      email: new FormControl(usuarioGestaoRawValue.email, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      senha: new FormControl(usuarioGestaoRawValue.senha, {
        validators: [Validators.required],
      }),
      token: new FormControl(usuarioGestaoRawValue.token),
      ativo: new FormControl(usuarioGestaoRawValue.ativo),
      dataHoraAtivacao: new FormControl(usuarioGestaoRawValue.dataHoraAtivacao),
      dataLimiteAcesso: new FormControl(usuarioGestaoRawValue.dataLimiteAcesso),
      situacaoUsuarioGestao: new FormControl(usuarioGestaoRawValue.situacaoUsuarioGestao),
      administrador: new FormControl(usuarioGestaoRawValue.administrador, {
        validators: [Validators.required],
      }),
    });
  }

  getUsuarioGestao(form: UsuarioGestaoFormGroup): IUsuarioGestao | NewUsuarioGestao {
    return this.convertUsuarioGestaoRawValueToUsuarioGestao(form.getRawValue() as UsuarioGestaoFormRawValue | NewUsuarioGestaoFormRawValue);
  }

  resetForm(form: UsuarioGestaoFormGroup, usuarioGestao: UsuarioGestaoFormGroupInput): void {
    const usuarioGestaoRawValue = this.convertUsuarioGestaoToUsuarioGestaoRawValue({ ...this.getFormDefaults(), ...usuarioGestao });
    form.reset(
      {
        ...usuarioGestaoRawValue,
        id: { value: usuarioGestaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UsuarioGestaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ativo: false,
      dataHoraAtivacao: currentTime,
      dataLimiteAcesso: currentTime,
    };
  }

  private convertUsuarioGestaoRawValueToUsuarioGestao(
    rawUsuarioGestao: UsuarioGestaoFormRawValue | NewUsuarioGestaoFormRawValue,
  ): IUsuarioGestao | NewUsuarioGestao {
    return {
      ...rawUsuarioGestao,
      dataHoraAtivacao: dayjs(rawUsuarioGestao.dataHoraAtivacao, DATE_TIME_FORMAT),
      dataLimiteAcesso: dayjs(rawUsuarioGestao.dataLimiteAcesso, DATE_TIME_FORMAT),
    };
  }

  private convertUsuarioGestaoToUsuarioGestaoRawValue(
    usuarioGestao: IUsuarioGestao | (Partial<NewUsuarioGestao> & UsuarioGestaoFormDefaults),
  ): UsuarioGestaoFormRawValue | PartialWithRequiredKeyOf<NewUsuarioGestaoFormRawValue> {
    return {
      ...usuarioGestao,
      dataHoraAtivacao: usuarioGestao.dataHoraAtivacao ? usuarioGestao.dataHoraAtivacao.format(DATE_TIME_FORMAT) : undefined,
      dataLimiteAcesso: usuarioGestao.dataLimiteAcesso ? usuarioGestao.dataLimiteAcesso.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
