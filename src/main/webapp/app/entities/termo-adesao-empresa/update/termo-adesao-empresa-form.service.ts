import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITermoAdesaoEmpresa, NewTermoAdesaoEmpresa } from '../termo-adesao-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITermoAdesaoEmpresa for edit and NewTermoAdesaoEmpresaFormGroupInput for create.
 */
type TermoAdesaoEmpresaFormGroupInput = ITermoAdesaoEmpresa | PartialWithRequiredKeyOf<NewTermoAdesaoEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITermoAdesaoEmpresa | NewTermoAdesaoEmpresa> = Omit<T, 'dataAdesao'> & {
  dataAdesao?: string | null;
};

type TermoAdesaoEmpresaFormRawValue = FormValueOf<ITermoAdesaoEmpresa>;

type NewTermoAdesaoEmpresaFormRawValue = FormValueOf<NewTermoAdesaoEmpresa>;

type TermoAdesaoEmpresaFormDefaults = Pick<NewTermoAdesaoEmpresa, 'id' | 'dataAdesao' | 'checked'>;

type TermoAdesaoEmpresaFormGroupContent = {
  id: FormControl<TermoAdesaoEmpresaFormRawValue['id'] | NewTermoAdesaoEmpresa['id']>;
  dataAdesao: FormControl<TermoAdesaoEmpresaFormRawValue['dataAdesao']>;
  checked: FormControl<TermoAdesaoEmpresaFormRawValue['checked']>;
  urlDoc: FormControl<TermoAdesaoEmpresaFormRawValue['urlDoc']>;
  empresa: FormControl<TermoAdesaoEmpresaFormRawValue['empresa']>;
  planoContabil: FormControl<TermoAdesaoEmpresaFormRawValue['planoContabil']>;
};

export type TermoAdesaoEmpresaFormGroup = FormGroup<TermoAdesaoEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TermoAdesaoEmpresaFormService {
  createTermoAdesaoEmpresaFormGroup(termoAdesaoEmpresa: TermoAdesaoEmpresaFormGroupInput = { id: null }): TermoAdesaoEmpresaFormGroup {
    const termoAdesaoEmpresaRawValue = this.convertTermoAdesaoEmpresaToTermoAdesaoEmpresaRawValue({
      ...this.getFormDefaults(),
      ...termoAdesaoEmpresa,
    });
    return new FormGroup<TermoAdesaoEmpresaFormGroupContent>({
      id: new FormControl(
        { value: termoAdesaoEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataAdesao: new FormControl(termoAdesaoEmpresaRawValue.dataAdesao),
      checked: new FormControl(termoAdesaoEmpresaRawValue.checked),
      urlDoc: new FormControl(termoAdesaoEmpresaRawValue.urlDoc),
      empresa: new FormControl(termoAdesaoEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
      planoContabil: new FormControl(termoAdesaoEmpresaRawValue.planoContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getTermoAdesaoEmpresa(form: TermoAdesaoEmpresaFormGroup): ITermoAdesaoEmpresa | NewTermoAdesaoEmpresa {
    return this.convertTermoAdesaoEmpresaRawValueToTermoAdesaoEmpresa(
      form.getRawValue() as TermoAdesaoEmpresaFormRawValue | NewTermoAdesaoEmpresaFormRawValue,
    );
  }

  resetForm(form: TermoAdesaoEmpresaFormGroup, termoAdesaoEmpresa: TermoAdesaoEmpresaFormGroupInput): void {
    const termoAdesaoEmpresaRawValue = this.convertTermoAdesaoEmpresaToTermoAdesaoEmpresaRawValue({
      ...this.getFormDefaults(),
      ...termoAdesaoEmpresa,
    });
    form.reset(
      {
        ...termoAdesaoEmpresaRawValue,
        id: { value: termoAdesaoEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TermoAdesaoEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAdesao: currentTime,
      checked: false,
    };
  }

  private convertTermoAdesaoEmpresaRawValueToTermoAdesaoEmpresa(
    rawTermoAdesaoEmpresa: TermoAdesaoEmpresaFormRawValue | NewTermoAdesaoEmpresaFormRawValue,
  ): ITermoAdesaoEmpresa | NewTermoAdesaoEmpresa {
    return {
      ...rawTermoAdesaoEmpresa,
      dataAdesao: dayjs(rawTermoAdesaoEmpresa.dataAdesao, DATE_TIME_FORMAT),
    };
  }

  private convertTermoAdesaoEmpresaToTermoAdesaoEmpresaRawValue(
    termoAdesaoEmpresa: ITermoAdesaoEmpresa | (Partial<NewTermoAdesaoEmpresa> & TermoAdesaoEmpresaFormDefaults),
  ): TermoAdesaoEmpresaFormRawValue | PartialWithRequiredKeyOf<NewTermoAdesaoEmpresaFormRawValue> {
    return {
      ...termoAdesaoEmpresa,
      dataAdesao: termoAdesaoEmpresa.dataAdesao ? termoAdesaoEmpresa.dataAdesao.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
