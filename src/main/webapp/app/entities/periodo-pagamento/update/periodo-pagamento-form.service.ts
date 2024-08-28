import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPeriodoPagamento, NewPeriodoPagamento } from '../periodo-pagamento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPeriodoPagamento for edit and NewPeriodoPagamentoFormGroupInput for create.
 */
type PeriodoPagamentoFormGroupInput = IPeriodoPagamento | PartialWithRequiredKeyOf<NewPeriodoPagamento>;

type PeriodoPagamentoFormDefaults = Pick<NewPeriodoPagamento, 'id'>;

type PeriodoPagamentoFormGroupContent = {
  id: FormControl<IPeriodoPagamento['id'] | NewPeriodoPagamento['id']>;
  periodo: FormControl<IPeriodoPagamento['periodo']>;
  numeroDias: FormControl<IPeriodoPagamento['numeroDias']>;
};

export type PeriodoPagamentoFormGroup = FormGroup<PeriodoPagamentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PeriodoPagamentoFormService {
  createPeriodoPagamentoFormGroup(periodoPagamento: PeriodoPagamentoFormGroupInput = { id: null }): PeriodoPagamentoFormGroup {
    const periodoPagamentoRawValue = {
      ...this.getFormDefaults(),
      ...periodoPagamento,
    };
    return new FormGroup<PeriodoPagamentoFormGroupContent>({
      id: new FormControl(
        { value: periodoPagamentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      periodo: new FormControl(periodoPagamentoRawValue.periodo),
      numeroDias: new FormControl(periodoPagamentoRawValue.numeroDias),
    });
  }

  getPeriodoPagamento(form: PeriodoPagamentoFormGroup): IPeriodoPagamento | NewPeriodoPagamento {
    return form.getRawValue() as IPeriodoPagamento | NewPeriodoPagamento;
  }

  resetForm(form: PeriodoPagamentoFormGroup, periodoPagamento: PeriodoPagamentoFormGroupInput): void {
    const periodoPagamentoRawValue = { ...this.getFormDefaults(), ...periodoPagamento };
    form.reset(
      {
        ...periodoPagamentoRawValue,
        id: { value: periodoPagamentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PeriodoPagamentoFormDefaults {
    return {
      id: null,
    };
  }
}
