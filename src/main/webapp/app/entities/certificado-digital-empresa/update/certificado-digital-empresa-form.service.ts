import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICertificadoDigitalEmpresa, NewCertificadoDigitalEmpresa } from '../certificado-digital-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICertificadoDigitalEmpresa for edit and NewCertificadoDigitalEmpresaFormGroupInput for create.
 */
type CertificadoDigitalEmpresaFormGroupInput = ICertificadoDigitalEmpresa | PartialWithRequiredKeyOf<NewCertificadoDigitalEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICertificadoDigitalEmpresa | NewCertificadoDigitalEmpresa> = Omit<T, 'dataContratacao' | 'dataVencimento'> & {
  dataContratacao?: string | null;
  dataVencimento?: string | null;
};

type CertificadoDigitalEmpresaFormRawValue = FormValueOf<ICertificadoDigitalEmpresa>;

type NewCertificadoDigitalEmpresaFormRawValue = FormValueOf<NewCertificadoDigitalEmpresa>;

type CertificadoDigitalEmpresaFormDefaults = Pick<NewCertificadoDigitalEmpresa, 'id' | 'dataContratacao' | 'dataVencimento'>;

type CertificadoDigitalEmpresaFormGroupContent = {
  id: FormControl<CertificadoDigitalEmpresaFormRawValue['id'] | NewCertificadoDigitalEmpresa['id']>;
  urlCertificado: FormControl<CertificadoDigitalEmpresaFormRawValue['urlCertificado']>;
  dataContratacao: FormControl<CertificadoDigitalEmpresaFormRawValue['dataContratacao']>;
  dataVencimento: FormControl<CertificadoDigitalEmpresaFormRawValue['dataVencimento']>;
  diasUso: FormControl<CertificadoDigitalEmpresaFormRawValue['diasUso']>;
  pessoaJuridica: FormControl<CertificadoDigitalEmpresaFormRawValue['pessoaJuridica']>;
  certificadoDigital: FormControl<CertificadoDigitalEmpresaFormRawValue['certificadoDigital']>;
  fornecedorCertificado: FormControl<CertificadoDigitalEmpresaFormRawValue['fornecedorCertificado']>;
};

export type CertificadoDigitalEmpresaFormGroup = FormGroup<CertificadoDigitalEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CertificadoDigitalEmpresaFormService {
  createCertificadoDigitalEmpresaFormGroup(
    certificadoDigitalEmpresa: CertificadoDigitalEmpresaFormGroupInput = { id: null },
  ): CertificadoDigitalEmpresaFormGroup {
    const certificadoDigitalEmpresaRawValue = this.convertCertificadoDigitalEmpresaToCertificadoDigitalEmpresaRawValue({
      ...this.getFormDefaults(),
      ...certificadoDigitalEmpresa,
    });
    return new FormGroup<CertificadoDigitalEmpresaFormGroupContent>({
      id: new FormControl(
        { value: certificadoDigitalEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      urlCertificado: new FormControl(certificadoDigitalEmpresaRawValue.urlCertificado, {
        validators: [Validators.required],
      }),
      dataContratacao: new FormControl(certificadoDigitalEmpresaRawValue.dataContratacao),
      dataVencimento: new FormControl(certificadoDigitalEmpresaRawValue.dataVencimento),
      diasUso: new FormControl(certificadoDigitalEmpresaRawValue.diasUso),
      pessoaJuridica: new FormControl(certificadoDigitalEmpresaRawValue.pessoaJuridica, {
        validators: [Validators.required],
      }),
      certificadoDigital: new FormControl(certificadoDigitalEmpresaRawValue.certificadoDigital, {
        validators: [Validators.required],
      }),
      fornecedorCertificado: new FormControl(certificadoDigitalEmpresaRawValue.fornecedorCertificado, {
        validators: [Validators.required],
      }),
    });
  }

  getCertificadoDigitalEmpresa(form: CertificadoDigitalEmpresaFormGroup): ICertificadoDigitalEmpresa | NewCertificadoDigitalEmpresa {
    return this.convertCertificadoDigitalEmpresaRawValueToCertificadoDigitalEmpresa(
      form.getRawValue() as CertificadoDigitalEmpresaFormRawValue | NewCertificadoDigitalEmpresaFormRawValue,
    );
  }

  resetForm(form: CertificadoDigitalEmpresaFormGroup, certificadoDigitalEmpresa: CertificadoDigitalEmpresaFormGroupInput): void {
    const certificadoDigitalEmpresaRawValue = this.convertCertificadoDigitalEmpresaToCertificadoDigitalEmpresaRawValue({
      ...this.getFormDefaults(),
      ...certificadoDigitalEmpresa,
    });
    form.reset(
      {
        ...certificadoDigitalEmpresaRawValue,
        id: { value: certificadoDigitalEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CertificadoDigitalEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataContratacao: currentTime,
      dataVencimento: currentTime,
    };
  }

  private convertCertificadoDigitalEmpresaRawValueToCertificadoDigitalEmpresa(
    rawCertificadoDigitalEmpresa: CertificadoDigitalEmpresaFormRawValue | NewCertificadoDigitalEmpresaFormRawValue,
  ): ICertificadoDigitalEmpresa | NewCertificadoDigitalEmpresa {
    return {
      ...rawCertificadoDigitalEmpresa,
      dataContratacao: dayjs(rawCertificadoDigitalEmpresa.dataContratacao, DATE_TIME_FORMAT),
      dataVencimento: dayjs(rawCertificadoDigitalEmpresa.dataVencimento, DATE_TIME_FORMAT),
    };
  }

  private convertCertificadoDigitalEmpresaToCertificadoDigitalEmpresaRawValue(
    certificadoDigitalEmpresa: ICertificadoDigitalEmpresa | (Partial<NewCertificadoDigitalEmpresa> & CertificadoDigitalEmpresaFormDefaults),
  ): CertificadoDigitalEmpresaFormRawValue | PartialWithRequiredKeyOf<NewCertificadoDigitalEmpresaFormRawValue> {
    return {
      ...certificadoDigitalEmpresa,
      dataContratacao: certificadoDigitalEmpresa.dataContratacao
        ? certificadoDigitalEmpresa.dataContratacao.format(DATE_TIME_FORMAT)
        : undefined,
      dataVencimento: certificadoDigitalEmpresa.dataVencimento
        ? certificadoDigitalEmpresa.dataVencimento.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
