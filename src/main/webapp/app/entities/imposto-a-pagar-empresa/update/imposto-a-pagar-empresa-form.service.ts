import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IImpostoAPagarEmpresa, NewImpostoAPagarEmpresa } from '../imposto-a-pagar-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IImpostoAPagarEmpresa for edit and NewImpostoAPagarEmpresaFormGroupInput for create.
 */
type ImpostoAPagarEmpresaFormGroupInput = IImpostoAPagarEmpresa | PartialWithRequiredKeyOf<NewImpostoAPagarEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IImpostoAPagarEmpresa | NewImpostoAPagarEmpresa> = Omit<T, 'dataVencimento' | 'dataPagamento'> & {
  dataVencimento?: string | null;
  dataPagamento?: string | null;
};

type ImpostoAPagarEmpresaFormRawValue = FormValueOf<IImpostoAPagarEmpresa>;

type NewImpostoAPagarEmpresaFormRawValue = FormValueOf<NewImpostoAPagarEmpresa>;

type ImpostoAPagarEmpresaFormDefaults = Pick<NewImpostoAPagarEmpresa, 'id' | 'dataVencimento' | 'dataPagamento'>;

type ImpostoAPagarEmpresaFormGroupContent = {
  id: FormControl<ImpostoAPagarEmpresaFormRawValue['id'] | NewImpostoAPagarEmpresa['id']>;
  dataVencimento: FormControl<ImpostoAPagarEmpresaFormRawValue['dataVencimento']>;
  dataPagamento: FormControl<ImpostoAPagarEmpresaFormRawValue['dataPagamento']>;
  valor: FormControl<ImpostoAPagarEmpresaFormRawValue['valor']>;
  valorMulta: FormControl<ImpostoAPagarEmpresaFormRawValue['valorMulta']>;
  urlArquivoPagamento: FormControl<ImpostoAPagarEmpresaFormRawValue['urlArquivoPagamento']>;
  urlArquivoComprovante: FormControl<ImpostoAPagarEmpresaFormRawValue['urlArquivoComprovante']>;
  situacaoPagamentoImpostoEnum: FormControl<ImpostoAPagarEmpresaFormRawValue['situacaoPagamentoImpostoEnum']>;
  imposto: FormControl<ImpostoAPagarEmpresaFormRawValue['imposto']>;
};

export type ImpostoAPagarEmpresaFormGroup = FormGroup<ImpostoAPagarEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ImpostoAPagarEmpresaFormService {
  createImpostoAPagarEmpresaFormGroup(
    impostoAPagarEmpresa: ImpostoAPagarEmpresaFormGroupInput = { id: null },
  ): ImpostoAPagarEmpresaFormGroup {
    const impostoAPagarEmpresaRawValue = this.convertImpostoAPagarEmpresaToImpostoAPagarEmpresaRawValue({
      ...this.getFormDefaults(),
      ...impostoAPagarEmpresa,
    });
    return new FormGroup<ImpostoAPagarEmpresaFormGroupContent>({
      id: new FormControl(
        { value: impostoAPagarEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataVencimento: new FormControl(impostoAPagarEmpresaRawValue.dataVencimento, {
        validators: [Validators.required],
      }),
      dataPagamento: new FormControl(impostoAPagarEmpresaRawValue.dataPagamento, {
        validators: [Validators.required],
      }),
      valor: new FormControl(impostoAPagarEmpresaRawValue.valor),
      valorMulta: new FormControl(impostoAPagarEmpresaRawValue.valorMulta),
      urlArquivoPagamento: new FormControl(impostoAPagarEmpresaRawValue.urlArquivoPagamento),
      urlArquivoComprovante: new FormControl(impostoAPagarEmpresaRawValue.urlArquivoComprovante),
      situacaoPagamentoImpostoEnum: new FormControl(impostoAPagarEmpresaRawValue.situacaoPagamentoImpostoEnum),
      imposto: new FormControl(impostoAPagarEmpresaRawValue.imposto, {
        validators: [Validators.required],
      }),
    });
  }

  getImpostoAPagarEmpresa(form: ImpostoAPagarEmpresaFormGroup): IImpostoAPagarEmpresa | NewImpostoAPagarEmpresa {
    return this.convertImpostoAPagarEmpresaRawValueToImpostoAPagarEmpresa(
      form.getRawValue() as ImpostoAPagarEmpresaFormRawValue | NewImpostoAPagarEmpresaFormRawValue,
    );
  }

  resetForm(form: ImpostoAPagarEmpresaFormGroup, impostoAPagarEmpresa: ImpostoAPagarEmpresaFormGroupInput): void {
    const impostoAPagarEmpresaRawValue = this.convertImpostoAPagarEmpresaToImpostoAPagarEmpresaRawValue({
      ...this.getFormDefaults(),
      ...impostoAPagarEmpresa,
    });
    form.reset(
      {
        ...impostoAPagarEmpresaRawValue,
        id: { value: impostoAPagarEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ImpostoAPagarEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataVencimento: currentTime,
      dataPagamento: currentTime,
    };
  }

  private convertImpostoAPagarEmpresaRawValueToImpostoAPagarEmpresa(
    rawImpostoAPagarEmpresa: ImpostoAPagarEmpresaFormRawValue | NewImpostoAPagarEmpresaFormRawValue,
  ): IImpostoAPagarEmpresa | NewImpostoAPagarEmpresa {
    return {
      ...rawImpostoAPagarEmpresa,
      dataVencimento: dayjs(rawImpostoAPagarEmpresa.dataVencimento, DATE_TIME_FORMAT),
      dataPagamento: dayjs(rawImpostoAPagarEmpresa.dataPagamento, DATE_TIME_FORMAT),
    };
  }

  private convertImpostoAPagarEmpresaToImpostoAPagarEmpresaRawValue(
    impostoAPagarEmpresa: IImpostoAPagarEmpresa | (Partial<NewImpostoAPagarEmpresa> & ImpostoAPagarEmpresaFormDefaults),
  ): ImpostoAPagarEmpresaFormRawValue | PartialWithRequiredKeyOf<NewImpostoAPagarEmpresaFormRawValue> {
    return {
      ...impostoAPagarEmpresa,
      dataVencimento: impostoAPagarEmpresa.dataVencimento ? impostoAPagarEmpresa.dataVencimento.format(DATE_TIME_FORMAT) : undefined,
      dataPagamento: impostoAPagarEmpresa.dataPagamento ? impostoAPagarEmpresa.dataPagamento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
