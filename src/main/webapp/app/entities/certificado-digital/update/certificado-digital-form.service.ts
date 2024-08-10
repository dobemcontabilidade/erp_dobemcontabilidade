import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICertificadoDigital, NewCertificadoDigital } from '../certificado-digital.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICertificadoDigital for edit and NewCertificadoDigitalFormGroupInput for create.
 */
type CertificadoDigitalFormGroupInput = ICertificadoDigital | PartialWithRequiredKeyOf<NewCertificadoDigital>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICertificadoDigital | NewCertificadoDigital> = Omit<T, 'dataContratacao'> & {
  dataContratacao?: string | null;
};

type CertificadoDigitalFormRawValue = FormValueOf<ICertificadoDigital>;

type NewCertificadoDigitalFormRawValue = FormValueOf<NewCertificadoDigital>;

type CertificadoDigitalFormDefaults = Pick<NewCertificadoDigital, 'id' | 'dataContratacao'>;

type CertificadoDigitalFormGroupContent = {
  id: FormControl<CertificadoDigitalFormRawValue['id'] | NewCertificadoDigital['id']>;
  urlCertificado: FormControl<CertificadoDigitalFormRawValue['urlCertificado']>;
  dataContratacao: FormControl<CertificadoDigitalFormRawValue['dataContratacao']>;
  validade: FormControl<CertificadoDigitalFormRawValue['validade']>;
  tipoCertificado: FormControl<CertificadoDigitalFormRawValue['tipoCertificado']>;
  empresa: FormControl<CertificadoDigitalFormRawValue['empresa']>;
};

export type CertificadoDigitalFormGroup = FormGroup<CertificadoDigitalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CertificadoDigitalFormService {
  createCertificadoDigitalFormGroup(certificadoDigital: CertificadoDigitalFormGroupInput = { id: null }): CertificadoDigitalFormGroup {
    const certificadoDigitalRawValue = this.convertCertificadoDigitalToCertificadoDigitalRawValue({
      ...this.getFormDefaults(),
      ...certificadoDigital,
    });
    return new FormGroup<CertificadoDigitalFormGroupContent>({
      id: new FormControl(
        { value: certificadoDigitalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      urlCertificado: new FormControl(certificadoDigitalRawValue.urlCertificado, {
        validators: [Validators.required],
      }),
      dataContratacao: new FormControl(certificadoDigitalRawValue.dataContratacao),
      validade: new FormControl(certificadoDigitalRawValue.validade),
      tipoCertificado: new FormControl(certificadoDigitalRawValue.tipoCertificado),
      empresa: new FormControl(certificadoDigitalRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getCertificadoDigital(form: CertificadoDigitalFormGroup): ICertificadoDigital | NewCertificadoDigital {
    return this.convertCertificadoDigitalRawValueToCertificadoDigital(
      form.getRawValue() as CertificadoDigitalFormRawValue | NewCertificadoDigitalFormRawValue,
    );
  }

  resetForm(form: CertificadoDigitalFormGroup, certificadoDigital: CertificadoDigitalFormGroupInput): void {
    const certificadoDigitalRawValue = this.convertCertificadoDigitalToCertificadoDigitalRawValue({
      ...this.getFormDefaults(),
      ...certificadoDigital,
    });
    form.reset(
      {
        ...certificadoDigitalRawValue,
        id: { value: certificadoDigitalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CertificadoDigitalFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataContratacao: currentTime,
    };
  }

  private convertCertificadoDigitalRawValueToCertificadoDigital(
    rawCertificadoDigital: CertificadoDigitalFormRawValue | NewCertificadoDigitalFormRawValue,
  ): ICertificadoDigital | NewCertificadoDigital {
    return {
      ...rawCertificadoDigital,
      dataContratacao: dayjs(rawCertificadoDigital.dataContratacao, DATE_TIME_FORMAT),
    };
  }

  private convertCertificadoDigitalToCertificadoDigitalRawValue(
    certificadoDigital: ICertificadoDigital | (Partial<NewCertificadoDigital> & CertificadoDigitalFormDefaults),
  ): CertificadoDigitalFormRawValue | PartialWithRequiredKeyOf<NewCertificadoDigitalFormRawValue> {
    return {
      ...certificadoDigital,
      dataContratacao: certificadoDigital.dataContratacao ? certificadoDigital.dataContratacao.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
