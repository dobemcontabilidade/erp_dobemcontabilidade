import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITermoContratoAssinaturaEmpresa, NewTermoContratoAssinaturaEmpresa } from '../termo-contrato-assinatura-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITermoContratoAssinaturaEmpresa for edit and NewTermoContratoAssinaturaEmpresaFormGroupInput for create.
 */
type TermoContratoAssinaturaEmpresaFormGroupInput =
  | ITermoContratoAssinaturaEmpresa
  | PartialWithRequiredKeyOf<NewTermoContratoAssinaturaEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITermoContratoAssinaturaEmpresa | NewTermoContratoAssinaturaEmpresa> = Omit<
  T,
  'dataAssinatura' | 'dataEnvioEmail'
> & {
  dataAssinatura?: string | null;
  dataEnvioEmail?: string | null;
};

type TermoContratoAssinaturaEmpresaFormRawValue = FormValueOf<ITermoContratoAssinaturaEmpresa>;

type NewTermoContratoAssinaturaEmpresaFormRawValue = FormValueOf<NewTermoContratoAssinaturaEmpresa>;

type TermoContratoAssinaturaEmpresaFormDefaults = Pick<NewTermoContratoAssinaturaEmpresa, 'id' | 'dataAssinatura' | 'dataEnvioEmail'>;

type TermoContratoAssinaturaEmpresaFormGroupContent = {
  id: FormControl<TermoContratoAssinaturaEmpresaFormRawValue['id'] | NewTermoContratoAssinaturaEmpresa['id']>;
  dataAssinatura: FormControl<TermoContratoAssinaturaEmpresaFormRawValue['dataAssinatura']>;
  dataEnvioEmail: FormControl<TermoContratoAssinaturaEmpresaFormRawValue['dataEnvioEmail']>;
  urlDocumentoAssinado: FormControl<TermoContratoAssinaturaEmpresaFormRawValue['urlDocumentoAssinado']>;
  situacao: FormControl<TermoContratoAssinaturaEmpresaFormRawValue['situacao']>;
  termoContratoContabil: FormControl<TermoContratoAssinaturaEmpresaFormRawValue['termoContratoContabil']>;
  empresa: FormControl<TermoContratoAssinaturaEmpresaFormRawValue['empresa']>;
};

export type TermoContratoAssinaturaEmpresaFormGroup = FormGroup<TermoContratoAssinaturaEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TermoContratoAssinaturaEmpresaFormService {
  createTermoContratoAssinaturaEmpresaFormGroup(
    termoContratoAssinaturaEmpresa: TermoContratoAssinaturaEmpresaFormGroupInput = { id: null },
  ): TermoContratoAssinaturaEmpresaFormGroup {
    const termoContratoAssinaturaEmpresaRawValue = this.convertTermoContratoAssinaturaEmpresaToTermoContratoAssinaturaEmpresaRawValue({
      ...this.getFormDefaults(),
      ...termoContratoAssinaturaEmpresa,
    });
    return new FormGroup<TermoContratoAssinaturaEmpresaFormGroupContent>({
      id: new FormControl(
        { value: termoContratoAssinaturaEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataAssinatura: new FormControl(termoContratoAssinaturaEmpresaRawValue.dataAssinatura),
      dataEnvioEmail: new FormControl(termoContratoAssinaturaEmpresaRawValue.dataEnvioEmail),
      urlDocumentoAssinado: new FormControl(termoContratoAssinaturaEmpresaRawValue.urlDocumentoAssinado),
      situacao: new FormControl(termoContratoAssinaturaEmpresaRawValue.situacao),
      termoContratoContabil: new FormControl(termoContratoAssinaturaEmpresaRawValue.termoContratoContabil, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(termoContratoAssinaturaEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getTermoContratoAssinaturaEmpresa(
    form: TermoContratoAssinaturaEmpresaFormGroup,
  ): ITermoContratoAssinaturaEmpresa | NewTermoContratoAssinaturaEmpresa {
    return this.convertTermoContratoAssinaturaEmpresaRawValueToTermoContratoAssinaturaEmpresa(
      form.getRawValue() as TermoContratoAssinaturaEmpresaFormRawValue | NewTermoContratoAssinaturaEmpresaFormRawValue,
    );
  }

  resetForm(
    form: TermoContratoAssinaturaEmpresaFormGroup,
    termoContratoAssinaturaEmpresa: TermoContratoAssinaturaEmpresaFormGroupInput,
  ): void {
    const termoContratoAssinaturaEmpresaRawValue = this.convertTermoContratoAssinaturaEmpresaToTermoContratoAssinaturaEmpresaRawValue({
      ...this.getFormDefaults(),
      ...termoContratoAssinaturaEmpresa,
    });
    form.reset(
      {
        ...termoContratoAssinaturaEmpresaRawValue,
        id: { value: termoContratoAssinaturaEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TermoContratoAssinaturaEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAssinatura: currentTime,
      dataEnvioEmail: currentTime,
    };
  }

  private convertTermoContratoAssinaturaEmpresaRawValueToTermoContratoAssinaturaEmpresa(
    rawTermoContratoAssinaturaEmpresa: TermoContratoAssinaturaEmpresaFormRawValue | NewTermoContratoAssinaturaEmpresaFormRawValue,
  ): ITermoContratoAssinaturaEmpresa | NewTermoContratoAssinaturaEmpresa {
    return {
      ...rawTermoContratoAssinaturaEmpresa,
      dataAssinatura: dayjs(rawTermoContratoAssinaturaEmpresa.dataAssinatura, DATE_TIME_FORMAT),
      dataEnvioEmail: dayjs(rawTermoContratoAssinaturaEmpresa.dataEnvioEmail, DATE_TIME_FORMAT),
    };
  }

  private convertTermoContratoAssinaturaEmpresaToTermoContratoAssinaturaEmpresaRawValue(
    termoContratoAssinaturaEmpresa:
      | ITermoContratoAssinaturaEmpresa
      | (Partial<NewTermoContratoAssinaturaEmpresa> & TermoContratoAssinaturaEmpresaFormDefaults),
  ): TermoContratoAssinaturaEmpresaFormRawValue | PartialWithRequiredKeyOf<NewTermoContratoAssinaturaEmpresaFormRawValue> {
    return {
      ...termoContratoAssinaturaEmpresa,
      dataAssinatura: termoContratoAssinaturaEmpresa.dataAssinatura
        ? termoContratoAssinaturaEmpresa.dataAssinatura.format(DATE_TIME_FORMAT)
        : undefined,
      dataEnvioEmail: termoContratoAssinaturaEmpresa.dataEnvioEmail
        ? termoContratoAssinaturaEmpresa.dataEnvioEmail.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
