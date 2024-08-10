import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmpresa, NewEmpresa } from '../empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmpresa for edit and NewEmpresaFormGroupInput for create.
 */
type EmpresaFormGroupInput = IEmpresa | PartialWithRequiredKeyOf<NewEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmpresa | NewEmpresa> = Omit<T, 'dataAbertura'> & {
  dataAbertura?: string | null;
};

type EmpresaFormRawValue = FormValueOf<IEmpresa>;

type NewEmpresaFormRawValue = FormValueOf<NewEmpresa>;

type EmpresaFormDefaults = Pick<NewEmpresa, 'id' | 'dataAbertura'>;

type EmpresaFormGroupContent = {
  id: FormControl<EmpresaFormRawValue['id'] | NewEmpresa['id']>;
  razaoSocial: FormControl<EmpresaFormRawValue['razaoSocial']>;
  nomeFantasia: FormControl<EmpresaFormRawValue['nomeFantasia']>;
  descricaoDoNegocio: FormControl<EmpresaFormRawValue['descricaoDoNegocio']>;
  cnpj: FormControl<EmpresaFormRawValue['cnpj']>;
  dataAbertura: FormControl<EmpresaFormRawValue['dataAbertura']>;
  urlContratoSocial: FormControl<EmpresaFormRawValue['urlContratoSocial']>;
  capitalSocial: FormControl<EmpresaFormRawValue['capitalSocial']>;
  ramo: FormControl<EmpresaFormRawValue['ramo']>;
  tributacao: FormControl<EmpresaFormRawValue['tributacao']>;
  enquadramento: FormControl<EmpresaFormRawValue['enquadramento']>;
};

export type EmpresaFormGroup = FormGroup<EmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpresaFormService {
  createEmpresaFormGroup(empresa: EmpresaFormGroupInput = { id: null }): EmpresaFormGroup {
    const empresaRawValue = this.convertEmpresaToEmpresaRawValue({
      ...this.getFormDefaults(),
      ...empresa,
    });
    return new FormGroup<EmpresaFormGroupContent>({
      id: new FormControl(
        { value: empresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      razaoSocial: new FormControl(empresaRawValue.razaoSocial, {
        validators: [Validators.required],
      }),
      nomeFantasia: new FormControl(empresaRawValue.nomeFantasia, {
        validators: [Validators.required],
      }),
      descricaoDoNegocio: new FormControl(empresaRawValue.descricaoDoNegocio),
      cnpj: new FormControl(empresaRawValue.cnpj, {
        validators: [Validators.maxLength(20)],
      }),
      dataAbertura: new FormControl(empresaRawValue.dataAbertura),
      urlContratoSocial: new FormControl(empresaRawValue.urlContratoSocial),
      capitalSocial: new FormControl(empresaRawValue.capitalSocial),
      ramo: new FormControl(empresaRawValue.ramo, {
        validators: [Validators.required],
      }),
      tributacao: new FormControl(empresaRawValue.tributacao, {
        validators: [Validators.required],
      }),
      enquadramento: new FormControl(empresaRawValue.enquadramento, {
        validators: [Validators.required],
      }),
    });
  }

  getEmpresa(form: EmpresaFormGroup): IEmpresa | NewEmpresa {
    return this.convertEmpresaRawValueToEmpresa(form.getRawValue() as EmpresaFormRawValue | NewEmpresaFormRawValue);
  }

  resetForm(form: EmpresaFormGroup, empresa: EmpresaFormGroupInput): void {
    const empresaRawValue = this.convertEmpresaToEmpresaRawValue({ ...this.getFormDefaults(), ...empresa });
    form.reset(
      {
        ...empresaRawValue,
        id: { value: empresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAbertura: currentTime,
    };
  }

  private convertEmpresaRawValueToEmpresa(rawEmpresa: EmpresaFormRawValue | NewEmpresaFormRawValue): IEmpresa | NewEmpresa {
    return {
      ...rawEmpresa,
      dataAbertura: dayjs(rawEmpresa.dataAbertura, DATE_TIME_FORMAT),
    };
  }

  private convertEmpresaToEmpresaRawValue(
    empresa: IEmpresa | (Partial<NewEmpresa> & EmpresaFormDefaults),
  ): EmpresaFormRawValue | PartialWithRequiredKeyOf<NewEmpresaFormRawValue> {
    return {
      ...empresa,
      dataAbertura: empresa.dataAbertura ? empresa.dataAbertura.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
