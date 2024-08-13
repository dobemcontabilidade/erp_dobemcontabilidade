import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ICobrancaEmpresa, NewCobrancaEmpresa } from '../cobranca-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICobrancaEmpresa for edit and NewCobrancaEmpresaFormGroupInput for create.
 */
type CobrancaEmpresaFormGroupInput = ICobrancaEmpresa | PartialWithRequiredKeyOf<NewCobrancaEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ICobrancaEmpresa | NewCobrancaEmpresa> = Omit<T, 'dataCobranca'> & {
  dataCobranca?: string | null;
};

type CobrancaEmpresaFormRawValue = FormValueOf<ICobrancaEmpresa>;

type NewCobrancaEmpresaFormRawValue = FormValueOf<NewCobrancaEmpresa>;

type CobrancaEmpresaFormDefaults = Pick<NewCobrancaEmpresa, 'id' | 'dataCobranca'>;

type CobrancaEmpresaFormGroupContent = {
  id: FormControl<CobrancaEmpresaFormRawValue['id'] | NewCobrancaEmpresa['id']>;
  dataCobranca: FormControl<CobrancaEmpresaFormRawValue['dataCobranca']>;
  valorPago: FormControl<CobrancaEmpresaFormRawValue['valorPago']>;
  urlCobranca: FormControl<CobrancaEmpresaFormRawValue['urlCobranca']>;
  urlArquivo: FormControl<CobrancaEmpresaFormRawValue['urlArquivo']>;
  valorCobrado: FormControl<CobrancaEmpresaFormRawValue['valorCobrado']>;
  situacaoCobranca: FormControl<CobrancaEmpresaFormRawValue['situacaoCobranca']>;
  assinaturaEmpresa: FormControl<CobrancaEmpresaFormRawValue['assinaturaEmpresa']>;
  formaDePagamento: FormControl<CobrancaEmpresaFormRawValue['formaDePagamento']>;
};

export type CobrancaEmpresaFormGroup = FormGroup<CobrancaEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CobrancaEmpresaFormService {
  createCobrancaEmpresaFormGroup(cobrancaEmpresa: CobrancaEmpresaFormGroupInput = { id: null }): CobrancaEmpresaFormGroup {
    const cobrancaEmpresaRawValue = this.convertCobrancaEmpresaToCobrancaEmpresaRawValue({
      ...this.getFormDefaults(),
      ...cobrancaEmpresa,
    });
    return new FormGroup<CobrancaEmpresaFormGroupContent>({
      id: new FormControl(
        { value: cobrancaEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataCobranca: new FormControl(cobrancaEmpresaRawValue.dataCobranca),
      valorPago: new FormControl(cobrancaEmpresaRawValue.valorPago),
      urlCobranca: new FormControl(cobrancaEmpresaRawValue.urlCobranca),
      urlArquivo: new FormControl(cobrancaEmpresaRawValue.urlArquivo),
      valorCobrado: new FormControl(cobrancaEmpresaRawValue.valorCobrado),
      situacaoCobranca: new FormControl(cobrancaEmpresaRawValue.situacaoCobranca),
      assinaturaEmpresa: new FormControl(cobrancaEmpresaRawValue.assinaturaEmpresa, {
        validators: [Validators.required],
      }),
      formaDePagamento: new FormControl(cobrancaEmpresaRawValue.formaDePagamento, {
        validators: [Validators.required],
      }),
    });
  }

  getCobrancaEmpresa(form: CobrancaEmpresaFormGroup): ICobrancaEmpresa | NewCobrancaEmpresa {
    return this.convertCobrancaEmpresaRawValueToCobrancaEmpresa(
      form.getRawValue() as CobrancaEmpresaFormRawValue | NewCobrancaEmpresaFormRawValue,
    );
  }

  resetForm(form: CobrancaEmpresaFormGroup, cobrancaEmpresa: CobrancaEmpresaFormGroupInput): void {
    const cobrancaEmpresaRawValue = this.convertCobrancaEmpresaToCobrancaEmpresaRawValue({ ...this.getFormDefaults(), ...cobrancaEmpresa });
    form.reset(
      {
        ...cobrancaEmpresaRawValue,
        id: { value: cobrancaEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CobrancaEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataCobranca: currentTime,
    };
  }

  private convertCobrancaEmpresaRawValueToCobrancaEmpresa(
    rawCobrancaEmpresa: CobrancaEmpresaFormRawValue | NewCobrancaEmpresaFormRawValue,
  ): ICobrancaEmpresa | NewCobrancaEmpresa {
    return {
      ...rawCobrancaEmpresa,
      dataCobranca: dayjs(rawCobrancaEmpresa.dataCobranca, DATE_TIME_FORMAT),
    };
  }

  private convertCobrancaEmpresaToCobrancaEmpresaRawValue(
    cobrancaEmpresa: ICobrancaEmpresa | (Partial<NewCobrancaEmpresa> & CobrancaEmpresaFormDefaults),
  ): CobrancaEmpresaFormRawValue | PartialWithRequiredKeyOf<NewCobrancaEmpresaFormRawValue> {
    return {
      ...cobrancaEmpresa,
      dataCobranca: cobrancaEmpresa.dataCobranca ? cobrancaEmpresa.dataCobranca.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
