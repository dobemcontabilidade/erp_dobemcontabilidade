import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUsuarioEmpresa, NewUsuarioEmpresa } from '../usuario-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUsuarioEmpresa for edit and NewUsuarioEmpresaFormGroupInput for create.
 */
type UsuarioEmpresaFormGroupInput = IUsuarioEmpresa | PartialWithRequiredKeyOf<NewUsuarioEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IUsuarioEmpresa | NewUsuarioEmpresa> = Omit<T, 'dataHoraAtivacao' | 'dataLimiteAcesso'> & {
  dataHoraAtivacao?: string | null;
  dataLimiteAcesso?: string | null;
};

type UsuarioEmpresaFormRawValue = FormValueOf<IUsuarioEmpresa>;

type NewUsuarioEmpresaFormRawValue = FormValueOf<NewUsuarioEmpresa>;

type UsuarioEmpresaFormDefaults = Pick<NewUsuarioEmpresa, 'id' | 'dataHoraAtivacao' | 'dataLimiteAcesso'>;

type UsuarioEmpresaFormGroupContent = {
  id: FormControl<UsuarioEmpresaFormRawValue['id'] | NewUsuarioEmpresa['id']>;
  email: FormControl<UsuarioEmpresaFormRawValue['email']>;
  senha: FormControl<UsuarioEmpresaFormRawValue['senha']>;
  token: FormControl<UsuarioEmpresaFormRawValue['token']>;
  dataHoraAtivacao: FormControl<UsuarioEmpresaFormRawValue['dataHoraAtivacao']>;
  dataLimiteAcesso: FormControl<UsuarioEmpresaFormRawValue['dataLimiteAcesso']>;
  situacao: FormControl<UsuarioEmpresaFormRawValue['situacao']>;
  pessoa: FormControl<UsuarioEmpresaFormRawValue['pessoa']>;
  empresa: FormControl<UsuarioEmpresaFormRawValue['empresa']>;
};

export type UsuarioEmpresaFormGroup = FormGroup<UsuarioEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UsuarioEmpresaFormService {
  createUsuarioEmpresaFormGroup(usuarioEmpresa: UsuarioEmpresaFormGroupInput = { id: null }): UsuarioEmpresaFormGroup {
    const usuarioEmpresaRawValue = this.convertUsuarioEmpresaToUsuarioEmpresaRawValue({
      ...this.getFormDefaults(),
      ...usuarioEmpresa,
    });
    return new FormGroup<UsuarioEmpresaFormGroupContent>({
      id: new FormControl(
        { value: usuarioEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      email: new FormControl(usuarioEmpresaRawValue.email, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      senha: new FormControl(usuarioEmpresaRawValue.senha, {
        validators: [Validators.required],
      }),
      token: new FormControl(usuarioEmpresaRawValue.token),
      dataHoraAtivacao: new FormControl(usuarioEmpresaRawValue.dataHoraAtivacao),
      dataLimiteAcesso: new FormControl(usuarioEmpresaRawValue.dataLimiteAcesso),
      situacao: new FormControl(usuarioEmpresaRawValue.situacao),
      pessoa: new FormControl(usuarioEmpresaRawValue.pessoa),
      empresa: new FormControl(usuarioEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getUsuarioEmpresa(form: UsuarioEmpresaFormGroup): IUsuarioEmpresa | NewUsuarioEmpresa {
    return this.convertUsuarioEmpresaRawValueToUsuarioEmpresa(
      form.getRawValue() as UsuarioEmpresaFormRawValue | NewUsuarioEmpresaFormRawValue,
    );
  }

  resetForm(form: UsuarioEmpresaFormGroup, usuarioEmpresa: UsuarioEmpresaFormGroupInput): void {
    const usuarioEmpresaRawValue = this.convertUsuarioEmpresaToUsuarioEmpresaRawValue({ ...this.getFormDefaults(), ...usuarioEmpresa });
    form.reset(
      {
        ...usuarioEmpresaRawValue,
        id: { value: usuarioEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UsuarioEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataHoraAtivacao: currentTime,
      dataLimiteAcesso: currentTime,
    };
  }

  private convertUsuarioEmpresaRawValueToUsuarioEmpresa(
    rawUsuarioEmpresa: UsuarioEmpresaFormRawValue | NewUsuarioEmpresaFormRawValue,
  ): IUsuarioEmpresa | NewUsuarioEmpresa {
    return {
      ...rawUsuarioEmpresa,
      dataHoraAtivacao: dayjs(rawUsuarioEmpresa.dataHoraAtivacao, DATE_TIME_FORMAT),
      dataLimiteAcesso: dayjs(rawUsuarioEmpresa.dataLimiteAcesso, DATE_TIME_FORMAT),
    };
  }

  private convertUsuarioEmpresaToUsuarioEmpresaRawValue(
    usuarioEmpresa: IUsuarioEmpresa | (Partial<NewUsuarioEmpresa> & UsuarioEmpresaFormDefaults),
  ): UsuarioEmpresaFormRawValue | PartialWithRequiredKeyOf<NewUsuarioEmpresaFormRawValue> {
    return {
      ...usuarioEmpresa,
      dataHoraAtivacao: usuarioEmpresa.dataHoraAtivacao ? usuarioEmpresa.dataHoraAtivacao.format(DATE_TIME_FORMAT) : undefined,
      dataLimiteAcesso: usuarioEmpresa.dataLimiteAcesso ? usuarioEmpresa.dataLimiteAcesso.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
