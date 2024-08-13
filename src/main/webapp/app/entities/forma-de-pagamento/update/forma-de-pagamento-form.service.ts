import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFormaDePagamento, NewFormaDePagamento } from '../forma-de-pagamento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFormaDePagamento for edit and NewFormaDePagamentoFormGroupInput for create.
 */
type FormaDePagamentoFormGroupInput = IFormaDePagamento | PartialWithRequiredKeyOf<NewFormaDePagamento>;

type FormaDePagamentoFormDefaults = Pick<NewFormaDePagamento, 'id' | 'disponivel'>;

type FormaDePagamentoFormGroupContent = {
  id: FormControl<IFormaDePagamento['id'] | NewFormaDePagamento['id']>;
  forma: FormControl<IFormaDePagamento['forma']>;
  disponivel: FormControl<IFormaDePagamento['disponivel']>;
};

export type FormaDePagamentoFormGroup = FormGroup<FormaDePagamentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FormaDePagamentoFormService {
  createFormaDePagamentoFormGroup(formaDePagamento: FormaDePagamentoFormGroupInput = { id: null }): FormaDePagamentoFormGroup {
    const formaDePagamentoRawValue = {
      ...this.getFormDefaults(),
      ...formaDePagamento,
    };
    return new FormGroup<FormaDePagamentoFormGroupContent>({
      id: new FormControl(
        { value: formaDePagamentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      forma: new FormControl(formaDePagamentoRawValue.forma),
      disponivel: new FormControl(formaDePagamentoRawValue.disponivel),
    });
  }

  getFormaDePagamento(form: FormaDePagamentoFormGroup): IFormaDePagamento | NewFormaDePagamento {
    return form.getRawValue() as IFormaDePagamento | NewFormaDePagamento;
  }

  resetForm(form: FormaDePagamentoFormGroup, formaDePagamento: FormaDePagamentoFormGroupInput): void {
    const formaDePagamentoRawValue = { ...this.getFormDefaults(), ...formaDePagamento };
    form.reset(
      {
        ...formaDePagamentoRawValue,
        id: { value: formaDePagamentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FormaDePagamentoFormDefaults {
    return {
      id: null,
      disponivel: false,
    };
  }
}
