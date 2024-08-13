import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IServicoContabilAssinaturaEmpresa, NewServicoContabilAssinaturaEmpresa } from '../servico-contabil-assinatura-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServicoContabilAssinaturaEmpresa for edit and NewServicoContabilAssinaturaEmpresaFormGroupInput for create.
 */
type ServicoContabilAssinaturaEmpresaFormGroupInput =
  | IServicoContabilAssinaturaEmpresa
  | PartialWithRequiredKeyOf<NewServicoContabilAssinaturaEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IServicoContabilAssinaturaEmpresa | NewServicoContabilAssinaturaEmpresa> = Omit<T, 'dataLegal' | 'dataAdmin'> & {
  dataLegal?: string | null;
  dataAdmin?: string | null;
};

type ServicoContabilAssinaturaEmpresaFormRawValue = FormValueOf<IServicoContabilAssinaturaEmpresa>;

type NewServicoContabilAssinaturaEmpresaFormRawValue = FormValueOf<NewServicoContabilAssinaturaEmpresa>;

type ServicoContabilAssinaturaEmpresaFormDefaults = Pick<NewServicoContabilAssinaturaEmpresa, 'id' | 'dataLegal' | 'dataAdmin'>;

type ServicoContabilAssinaturaEmpresaFormGroupContent = {
  id: FormControl<ServicoContabilAssinaturaEmpresaFormRawValue['id'] | NewServicoContabilAssinaturaEmpresa['id']>;
  dataLegal: FormControl<ServicoContabilAssinaturaEmpresaFormRawValue['dataLegal']>;
  dataAdmin: FormControl<ServicoContabilAssinaturaEmpresaFormRawValue['dataAdmin']>;
  servicoContabil: FormControl<ServicoContabilAssinaturaEmpresaFormRawValue['servicoContabil']>;
  assinaturaEmpresa: FormControl<ServicoContabilAssinaturaEmpresaFormRawValue['assinaturaEmpresa']>;
};

export type ServicoContabilAssinaturaEmpresaFormGroup = FormGroup<ServicoContabilAssinaturaEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilAssinaturaEmpresaFormService {
  createServicoContabilAssinaturaEmpresaFormGroup(
    servicoContabilAssinaturaEmpresa: ServicoContabilAssinaturaEmpresaFormGroupInput = { id: null },
  ): ServicoContabilAssinaturaEmpresaFormGroup {
    const servicoContabilAssinaturaEmpresaRawValue = this.convertServicoContabilAssinaturaEmpresaToServicoContabilAssinaturaEmpresaRawValue(
      {
        ...this.getFormDefaults(),
        ...servicoContabilAssinaturaEmpresa,
      },
    );
    return new FormGroup<ServicoContabilAssinaturaEmpresaFormGroupContent>({
      id: new FormControl(
        { value: servicoContabilAssinaturaEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataLegal: new FormControl(servicoContabilAssinaturaEmpresaRawValue.dataLegal, {
        validators: [Validators.required],
      }),
      dataAdmin: new FormControl(servicoContabilAssinaturaEmpresaRawValue.dataAdmin, {
        validators: [Validators.required],
      }),
      servicoContabil: new FormControl(servicoContabilAssinaturaEmpresaRawValue.servicoContabil, {
        validators: [Validators.required],
      }),
      assinaturaEmpresa: new FormControl(servicoContabilAssinaturaEmpresaRawValue.assinaturaEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getServicoContabilAssinaturaEmpresa(
    form: ServicoContabilAssinaturaEmpresaFormGroup,
  ): IServicoContabilAssinaturaEmpresa | NewServicoContabilAssinaturaEmpresa {
    return this.convertServicoContabilAssinaturaEmpresaRawValueToServicoContabilAssinaturaEmpresa(
      form.getRawValue() as ServicoContabilAssinaturaEmpresaFormRawValue | NewServicoContabilAssinaturaEmpresaFormRawValue,
    );
  }

  resetForm(
    form: ServicoContabilAssinaturaEmpresaFormGroup,
    servicoContabilAssinaturaEmpresa: ServicoContabilAssinaturaEmpresaFormGroupInput,
  ): void {
    const servicoContabilAssinaturaEmpresaRawValue = this.convertServicoContabilAssinaturaEmpresaToServicoContabilAssinaturaEmpresaRawValue(
      { ...this.getFormDefaults(), ...servicoContabilAssinaturaEmpresa },
    );
    form.reset(
      {
        ...servicoContabilAssinaturaEmpresaRawValue,
        id: { value: servicoContabilAssinaturaEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServicoContabilAssinaturaEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataLegal: currentTime,
      dataAdmin: currentTime,
    };
  }

  private convertServicoContabilAssinaturaEmpresaRawValueToServicoContabilAssinaturaEmpresa(
    rawServicoContabilAssinaturaEmpresa: ServicoContabilAssinaturaEmpresaFormRawValue | NewServicoContabilAssinaturaEmpresaFormRawValue,
  ): IServicoContabilAssinaturaEmpresa | NewServicoContabilAssinaturaEmpresa {
    return {
      ...rawServicoContabilAssinaturaEmpresa,
      dataLegal: dayjs(rawServicoContabilAssinaturaEmpresa.dataLegal, DATE_TIME_FORMAT),
      dataAdmin: dayjs(rawServicoContabilAssinaturaEmpresa.dataAdmin, DATE_TIME_FORMAT),
    };
  }

  private convertServicoContabilAssinaturaEmpresaToServicoContabilAssinaturaEmpresaRawValue(
    servicoContabilAssinaturaEmpresa:
      | IServicoContabilAssinaturaEmpresa
      | (Partial<NewServicoContabilAssinaturaEmpresa> & ServicoContabilAssinaturaEmpresaFormDefaults),
  ): ServicoContabilAssinaturaEmpresaFormRawValue | PartialWithRequiredKeyOf<NewServicoContabilAssinaturaEmpresaFormRawValue> {
    return {
      ...servicoContabilAssinaturaEmpresa,
      dataLegal: servicoContabilAssinaturaEmpresa.dataLegal
        ? servicoContabilAssinaturaEmpresa.dataLegal.format(DATE_TIME_FORMAT)
        : undefined,
      dataAdmin: servicoContabilAssinaturaEmpresa.dataAdmin
        ? servicoContabilAssinaturaEmpresa.dataAdmin.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
