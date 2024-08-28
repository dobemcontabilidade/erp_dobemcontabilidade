import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDescontoPeriodoPagamento, NewDescontoPeriodoPagamento } from '../desconto-periodo-pagamento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDescontoPeriodoPagamento for edit and NewDescontoPeriodoPagamentoFormGroupInput for create.
 */
type DescontoPeriodoPagamentoFormGroupInput = IDescontoPeriodoPagamento | PartialWithRequiredKeyOf<NewDescontoPeriodoPagamento>;

type DescontoPeriodoPagamentoFormDefaults = Pick<NewDescontoPeriodoPagamento, 'id'>;

type DescontoPeriodoPagamentoFormGroupContent = {
  id: FormControl<IDescontoPeriodoPagamento['id'] | NewDescontoPeriodoPagamento['id']>;
  percentual: FormControl<IDescontoPeriodoPagamento['percentual']>;
  periodoPagamento: FormControl<IDescontoPeriodoPagamento['periodoPagamento']>;
  planoAssinaturaContabil: FormControl<IDescontoPeriodoPagamento['planoAssinaturaContabil']>;
};

export type DescontoPeriodoPagamentoFormGroup = FormGroup<DescontoPeriodoPagamentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DescontoPeriodoPagamentoFormService {
  createDescontoPeriodoPagamentoFormGroup(
    descontoPeriodoPagamento: DescontoPeriodoPagamentoFormGroupInput = { id: null },
  ): DescontoPeriodoPagamentoFormGroup {
    const descontoPeriodoPagamentoRawValue = {
      ...this.getFormDefaults(),
      ...descontoPeriodoPagamento,
    };
    return new FormGroup<DescontoPeriodoPagamentoFormGroupContent>({
      id: new FormControl(
        { value: descontoPeriodoPagamentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      percentual: new FormControl(descontoPeriodoPagamentoRawValue.percentual),
      periodoPagamento: new FormControl(descontoPeriodoPagamentoRawValue.periodoPagamento, {
        validators: [Validators.required],
      }),
      planoAssinaturaContabil: new FormControl(descontoPeriodoPagamentoRawValue.planoAssinaturaContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getDescontoPeriodoPagamento(form: DescontoPeriodoPagamentoFormGroup): IDescontoPeriodoPagamento | NewDescontoPeriodoPagamento {
    return form.getRawValue() as IDescontoPeriodoPagamento | NewDescontoPeriodoPagamento;
  }

  resetForm(form: DescontoPeriodoPagamentoFormGroup, descontoPeriodoPagamento: DescontoPeriodoPagamentoFormGroupInput): void {
    const descontoPeriodoPagamentoRawValue = { ...this.getFormDefaults(), ...descontoPeriodoPagamento };
    form.reset(
      {
        ...descontoPeriodoPagamentoRawValue,
        id: { value: descontoPeriodoPagamentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DescontoPeriodoPagamentoFormDefaults {
    return {
      id: null,
    };
  }
}
