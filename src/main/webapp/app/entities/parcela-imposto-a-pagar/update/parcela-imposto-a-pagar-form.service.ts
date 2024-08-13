import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IParcelaImpostoAPagar, NewParcelaImpostoAPagar } from '../parcela-imposto-a-pagar.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IParcelaImpostoAPagar for edit and NewParcelaImpostoAPagarFormGroupInput for create.
 */
type ParcelaImpostoAPagarFormGroupInput = IParcelaImpostoAPagar | PartialWithRequiredKeyOf<NewParcelaImpostoAPagar>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IParcelaImpostoAPagar | NewParcelaImpostoAPagar> = Omit<T, 'dataVencimento' | 'dataPagamento'> & {
  dataVencimento?: string | null;
  dataPagamento?: string | null;
};

type ParcelaImpostoAPagarFormRawValue = FormValueOf<IParcelaImpostoAPagar>;

type NewParcelaImpostoAPagarFormRawValue = FormValueOf<NewParcelaImpostoAPagar>;

type ParcelaImpostoAPagarFormDefaults = Pick<NewParcelaImpostoAPagar, 'id' | 'dataVencimento' | 'dataPagamento'>;

type ParcelaImpostoAPagarFormGroupContent = {
  id: FormControl<ParcelaImpostoAPagarFormRawValue['id'] | NewParcelaImpostoAPagar['id']>;
  numeroParcela: FormControl<ParcelaImpostoAPagarFormRawValue['numeroParcela']>;
  dataVencimento: FormControl<ParcelaImpostoAPagarFormRawValue['dataVencimento']>;
  dataPagamento: FormControl<ParcelaImpostoAPagarFormRawValue['dataPagamento']>;
  valor: FormControl<ParcelaImpostoAPagarFormRawValue['valor']>;
  valorMulta: FormControl<ParcelaImpostoAPagarFormRawValue['valorMulta']>;
  urlArquivoPagamento: FormControl<ParcelaImpostoAPagarFormRawValue['urlArquivoPagamento']>;
  urlArquivoComprovante: FormControl<ParcelaImpostoAPagarFormRawValue['urlArquivoComprovante']>;
  mesCompetencia: FormControl<ParcelaImpostoAPagarFormRawValue['mesCompetencia']>;
  parcelamentoImposto: FormControl<ParcelaImpostoAPagarFormRawValue['parcelamentoImposto']>;
};

export type ParcelaImpostoAPagarFormGroup = FormGroup<ParcelaImpostoAPagarFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ParcelaImpostoAPagarFormService {
  createParcelaImpostoAPagarFormGroup(
    parcelaImpostoAPagar: ParcelaImpostoAPagarFormGroupInput = { id: null },
  ): ParcelaImpostoAPagarFormGroup {
    const parcelaImpostoAPagarRawValue = this.convertParcelaImpostoAPagarToParcelaImpostoAPagarRawValue({
      ...this.getFormDefaults(),
      ...parcelaImpostoAPagar,
    });
    return new FormGroup<ParcelaImpostoAPagarFormGroupContent>({
      id: new FormControl(
        { value: parcelaImpostoAPagarRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      numeroParcela: new FormControl(parcelaImpostoAPagarRawValue.numeroParcela),
      dataVencimento: new FormControl(parcelaImpostoAPagarRawValue.dataVencimento),
      dataPagamento: new FormControl(parcelaImpostoAPagarRawValue.dataPagamento),
      valor: new FormControl(parcelaImpostoAPagarRawValue.valor),
      valorMulta: new FormControl(parcelaImpostoAPagarRawValue.valorMulta),
      urlArquivoPagamento: new FormControl(parcelaImpostoAPagarRawValue.urlArquivoPagamento),
      urlArquivoComprovante: new FormControl(parcelaImpostoAPagarRawValue.urlArquivoComprovante),
      mesCompetencia: new FormControl(parcelaImpostoAPagarRawValue.mesCompetencia),
      parcelamentoImposto: new FormControl(parcelaImpostoAPagarRawValue.parcelamentoImposto, {
        validators: [Validators.required],
      }),
    });
  }

  getParcelaImpostoAPagar(form: ParcelaImpostoAPagarFormGroup): IParcelaImpostoAPagar | NewParcelaImpostoAPagar {
    return this.convertParcelaImpostoAPagarRawValueToParcelaImpostoAPagar(
      form.getRawValue() as ParcelaImpostoAPagarFormRawValue | NewParcelaImpostoAPagarFormRawValue,
    );
  }

  resetForm(form: ParcelaImpostoAPagarFormGroup, parcelaImpostoAPagar: ParcelaImpostoAPagarFormGroupInput): void {
    const parcelaImpostoAPagarRawValue = this.convertParcelaImpostoAPagarToParcelaImpostoAPagarRawValue({
      ...this.getFormDefaults(),
      ...parcelaImpostoAPagar,
    });
    form.reset(
      {
        ...parcelaImpostoAPagarRawValue,
        id: { value: parcelaImpostoAPagarRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ParcelaImpostoAPagarFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataVencimento: currentTime,
      dataPagamento: currentTime,
    };
  }

  private convertParcelaImpostoAPagarRawValueToParcelaImpostoAPagar(
    rawParcelaImpostoAPagar: ParcelaImpostoAPagarFormRawValue | NewParcelaImpostoAPagarFormRawValue,
  ): IParcelaImpostoAPagar | NewParcelaImpostoAPagar {
    return {
      ...rawParcelaImpostoAPagar,
      dataVencimento: dayjs(rawParcelaImpostoAPagar.dataVencimento, DATE_TIME_FORMAT),
      dataPagamento: dayjs(rawParcelaImpostoAPagar.dataPagamento, DATE_TIME_FORMAT),
    };
  }

  private convertParcelaImpostoAPagarToParcelaImpostoAPagarRawValue(
    parcelaImpostoAPagar: IParcelaImpostoAPagar | (Partial<NewParcelaImpostoAPagar> & ParcelaImpostoAPagarFormDefaults),
  ): ParcelaImpostoAPagarFormRawValue | PartialWithRequiredKeyOf<NewParcelaImpostoAPagarFormRawValue> {
    return {
      ...parcelaImpostoAPagar,
      dataVencimento: parcelaImpostoAPagar.dataVencimento ? parcelaImpostoAPagar.dataVencimento.format(DATE_TIME_FORMAT) : undefined,
      dataPagamento: parcelaImpostoAPagar.dataPagamento ? parcelaImpostoAPagar.dataPagamento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
