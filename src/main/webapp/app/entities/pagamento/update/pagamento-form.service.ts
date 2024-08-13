import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IPagamento, NewPagamento } from '../pagamento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPagamento for edit and NewPagamentoFormGroupInput for create.
 */
type PagamentoFormGroupInput = IPagamento | PartialWithRequiredKeyOf<NewPagamento>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IPagamento | NewPagamento> = Omit<T, 'dataCobranca' | 'dataVencimento' | 'dataPagamento'> & {
  dataCobranca?: string | null;
  dataVencimento?: string | null;
  dataPagamento?: string | null;
};

type PagamentoFormRawValue = FormValueOf<IPagamento>;

type NewPagamentoFormRawValue = FormValueOf<NewPagamento>;

type PagamentoFormDefaults = Pick<NewPagamento, 'id' | 'dataCobranca' | 'dataVencimento' | 'dataPagamento'>;

type PagamentoFormGroupContent = {
  id: FormControl<PagamentoFormRawValue['id'] | NewPagamento['id']>;
  dataCobranca: FormControl<PagamentoFormRawValue['dataCobranca']>;
  dataVencimento: FormControl<PagamentoFormRawValue['dataVencimento']>;
  dataPagamento: FormControl<PagamentoFormRawValue['dataPagamento']>;
  valorPago: FormControl<PagamentoFormRawValue['valorPago']>;
  valorCobrado: FormControl<PagamentoFormRawValue['valorCobrado']>;
  acrescimo: FormControl<PagamentoFormRawValue['acrescimo']>;
  multa: FormControl<PagamentoFormRawValue['multa']>;
  juros: FormControl<PagamentoFormRawValue['juros']>;
  situacao: FormControl<PagamentoFormRawValue['situacao']>;
  assinaturaEmpresa: FormControl<PagamentoFormRawValue['assinaturaEmpresa']>;
};

export type PagamentoFormGroup = FormGroup<PagamentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PagamentoFormService {
  createPagamentoFormGroup(pagamento: PagamentoFormGroupInput = { id: null }): PagamentoFormGroup {
    const pagamentoRawValue = this.convertPagamentoToPagamentoRawValue({
      ...this.getFormDefaults(),
      ...pagamento,
    });
    return new FormGroup<PagamentoFormGroupContent>({
      id: new FormControl(
        { value: pagamentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataCobranca: new FormControl(pagamentoRawValue.dataCobranca),
      dataVencimento: new FormControl(pagamentoRawValue.dataVencimento),
      dataPagamento: new FormControl(pagamentoRawValue.dataPagamento),
      valorPago: new FormControl(pagamentoRawValue.valorPago),
      valorCobrado: new FormControl(pagamentoRawValue.valorCobrado),
      acrescimo: new FormControl(pagamentoRawValue.acrescimo),
      multa: new FormControl(pagamentoRawValue.multa),
      juros: new FormControl(pagamentoRawValue.juros),
      situacao: new FormControl(pagamentoRawValue.situacao, {
        validators: [Validators.required],
      }),
      assinaturaEmpresa: new FormControl(pagamentoRawValue.assinaturaEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getPagamento(form: PagamentoFormGroup): IPagamento | NewPagamento {
    return this.convertPagamentoRawValueToPagamento(form.getRawValue() as PagamentoFormRawValue | NewPagamentoFormRawValue);
  }

  resetForm(form: PagamentoFormGroup, pagamento: PagamentoFormGroupInput): void {
    const pagamentoRawValue = this.convertPagamentoToPagamentoRawValue({ ...this.getFormDefaults(), ...pagamento });
    form.reset(
      {
        ...pagamentoRawValue,
        id: { value: pagamentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PagamentoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataCobranca: currentTime,
      dataVencimento: currentTime,
      dataPagamento: currentTime,
    };
  }

  private convertPagamentoRawValueToPagamento(rawPagamento: PagamentoFormRawValue | NewPagamentoFormRawValue): IPagamento | NewPagamento {
    return {
      ...rawPagamento,
      dataCobranca: dayjs(rawPagamento.dataCobranca, DATE_TIME_FORMAT),
      dataVencimento: dayjs(rawPagamento.dataVencimento, DATE_TIME_FORMAT),
      dataPagamento: dayjs(rawPagamento.dataPagamento, DATE_TIME_FORMAT),
    };
  }

  private convertPagamentoToPagamentoRawValue(
    pagamento: IPagamento | (Partial<NewPagamento> & PagamentoFormDefaults),
  ): PagamentoFormRawValue | PartialWithRequiredKeyOf<NewPagamentoFormRawValue> {
    return {
      ...pagamento,
      dataCobranca: pagamento.dataCobranca ? pagamento.dataCobranca.format(DATE_TIME_FORMAT) : undefined,
      dataVencimento: pagamento.dataVencimento ? pagamento.dataVencimento.format(DATE_TIME_FORMAT) : undefined,
      dataPagamento: pagamento.dataPagamento ? pagamento.dataPagamento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
