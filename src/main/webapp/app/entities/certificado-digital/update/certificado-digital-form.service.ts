import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

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

type CertificadoDigitalFormDefaults = Pick<NewCertificadoDigital, 'id'>;

type CertificadoDigitalFormGroupContent = {
  id: FormControl<ICertificadoDigital['id'] | NewCertificadoDigital['id']>;
  nome: FormControl<ICertificadoDigital['nome']>;
  sigla: FormControl<ICertificadoDigital['sigla']>;
  descricao: FormControl<ICertificadoDigital['descricao']>;
  tipoCertificado: FormControl<ICertificadoDigital['tipoCertificado']>;
};

export type CertificadoDigitalFormGroup = FormGroup<CertificadoDigitalFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CertificadoDigitalFormService {
  createCertificadoDigitalFormGroup(certificadoDigital: CertificadoDigitalFormGroupInput = { id: null }): CertificadoDigitalFormGroup {
    const certificadoDigitalRawValue = {
      ...this.getFormDefaults(),
      ...certificadoDigital,
    };
    return new FormGroup<CertificadoDigitalFormGroupContent>({
      id: new FormControl(
        { value: certificadoDigitalRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(certificadoDigitalRawValue.nome, {
        validators: [Validators.maxLength(300)],
      }),
      sigla: new FormControl(certificadoDigitalRawValue.sigla, {
        validators: [Validators.maxLength(20)],
      }),
      descricao: new FormControl(certificadoDigitalRawValue.descricao),
      tipoCertificado: new FormControl(certificadoDigitalRawValue.tipoCertificado),
    });
  }

  getCertificadoDigital(form: CertificadoDigitalFormGroup): ICertificadoDigital | NewCertificadoDigital {
    return form.getRawValue() as ICertificadoDigital | NewCertificadoDigital;
  }

  resetForm(form: CertificadoDigitalFormGroup, certificadoDigital: CertificadoDigitalFormGroupInput): void {
    const certificadoDigitalRawValue = { ...this.getFormDefaults(), ...certificadoDigital };
    form.reset(
      {
        ...certificadoDigitalRawValue,
        id: { value: certificadoDigitalRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CertificadoDigitalFormDefaults {
    return {
      id: null,
    };
  }
}
