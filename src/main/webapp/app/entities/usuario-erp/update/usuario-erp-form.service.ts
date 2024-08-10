import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IUsuarioErp, NewUsuarioErp } from '../usuario-erp.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IUsuarioErp for edit and NewUsuarioErpFormGroupInput for create.
 */
type UsuarioErpFormGroupInput = IUsuarioErp | PartialWithRequiredKeyOf<NewUsuarioErp>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IUsuarioErp | NewUsuarioErp> = Omit<T, 'dataHoraAtivacao' | 'dataLimiteAcesso'> & {
  dataHoraAtivacao?: string | null;
  dataLimiteAcesso?: string | null;
};

type UsuarioErpFormRawValue = FormValueOf<IUsuarioErp>;

type NewUsuarioErpFormRawValue = FormValueOf<NewUsuarioErp>;

type UsuarioErpFormDefaults = Pick<NewUsuarioErp, 'id' | 'dataHoraAtivacao' | 'dataLimiteAcesso'>;

type UsuarioErpFormGroupContent = {
  id: FormControl<UsuarioErpFormRawValue['id'] | NewUsuarioErp['id']>;
  email: FormControl<UsuarioErpFormRawValue['email']>;
  senha: FormControl<UsuarioErpFormRawValue['senha']>;
  dataHoraAtivacao: FormControl<UsuarioErpFormRawValue['dataHoraAtivacao']>;
  dataLimiteAcesso: FormControl<UsuarioErpFormRawValue['dataLimiteAcesso']>;
  situacao: FormControl<UsuarioErpFormRawValue['situacao']>;
  administrador: FormControl<UsuarioErpFormRawValue['administrador']>;
};

export type UsuarioErpFormGroup = FormGroup<UsuarioErpFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class UsuarioErpFormService {
  createUsuarioErpFormGroup(usuarioErp: UsuarioErpFormGroupInput = { id: null }): UsuarioErpFormGroup {
    const usuarioErpRawValue = this.convertUsuarioErpToUsuarioErpRawValue({
      ...this.getFormDefaults(),
      ...usuarioErp,
    });
    return new FormGroup<UsuarioErpFormGroupContent>({
      id: new FormControl(
        { value: usuarioErpRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      email: new FormControl(usuarioErpRawValue.email, {
        validators: [Validators.required, Validators.maxLength(200)],
      }),
      senha: new FormControl(usuarioErpRawValue.senha, {
        validators: [Validators.required],
      }),
      dataHoraAtivacao: new FormControl(usuarioErpRawValue.dataHoraAtivacao),
      dataLimiteAcesso: new FormControl(usuarioErpRawValue.dataLimiteAcesso),
      situacao: new FormControl(usuarioErpRawValue.situacao),
      administrador: new FormControl(usuarioErpRawValue.administrador, {
        validators: [Validators.required],
      }),
    });
  }

  getUsuarioErp(form: UsuarioErpFormGroup): IUsuarioErp | NewUsuarioErp {
    return this.convertUsuarioErpRawValueToUsuarioErp(form.getRawValue() as UsuarioErpFormRawValue | NewUsuarioErpFormRawValue);
  }

  resetForm(form: UsuarioErpFormGroup, usuarioErp: UsuarioErpFormGroupInput): void {
    const usuarioErpRawValue = this.convertUsuarioErpToUsuarioErpRawValue({ ...this.getFormDefaults(), ...usuarioErp });
    form.reset(
      {
        ...usuarioErpRawValue,
        id: { value: usuarioErpRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): UsuarioErpFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataHoraAtivacao: currentTime,
      dataLimiteAcesso: currentTime,
    };
  }

  private convertUsuarioErpRawValueToUsuarioErp(
    rawUsuarioErp: UsuarioErpFormRawValue | NewUsuarioErpFormRawValue,
  ): IUsuarioErp | NewUsuarioErp {
    return {
      ...rawUsuarioErp,
      dataHoraAtivacao: dayjs(rawUsuarioErp.dataHoraAtivacao, DATE_TIME_FORMAT),
      dataLimiteAcesso: dayjs(rawUsuarioErp.dataLimiteAcesso, DATE_TIME_FORMAT),
    };
  }

  private convertUsuarioErpToUsuarioErpRawValue(
    usuarioErp: IUsuarioErp | (Partial<NewUsuarioErp> & UsuarioErpFormDefaults),
  ): UsuarioErpFormRawValue | PartialWithRequiredKeyOf<NewUsuarioErpFormRawValue> {
    return {
      ...usuarioErp,
      dataHoraAtivacao: usuarioErp.dataHoraAtivacao ? usuarioErp.dataHoraAtivacao.format(DATE_TIME_FORMAT) : undefined,
      dataLimiteAcesso: usuarioErp.dataLimiteAcesso ? usuarioErp.dataLimiteAcesso.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
