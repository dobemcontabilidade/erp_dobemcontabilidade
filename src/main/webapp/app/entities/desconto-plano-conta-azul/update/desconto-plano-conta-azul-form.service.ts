import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDescontoPlanoContaAzul, NewDescontoPlanoContaAzul } from '../desconto-plano-conta-azul.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDescontoPlanoContaAzul for edit and NewDescontoPlanoContaAzulFormGroupInput for create.
 */
type DescontoPlanoContaAzulFormGroupInput = IDescontoPlanoContaAzul | PartialWithRequiredKeyOf<NewDescontoPlanoContaAzul>;

type DescontoPlanoContaAzulFormDefaults = Pick<NewDescontoPlanoContaAzul, 'id'>;

type DescontoPlanoContaAzulFormGroupContent = {
  id: FormControl<IDescontoPlanoContaAzul['id'] | NewDescontoPlanoContaAzul['id']>;
  percentual: FormControl<IDescontoPlanoContaAzul['percentual']>;
  planoContaAzul: FormControl<IDescontoPlanoContaAzul['planoContaAzul']>;
  periodoPagamento: FormControl<IDescontoPlanoContaAzul['periodoPagamento']>;
};

export type DescontoPlanoContaAzulFormGroup = FormGroup<DescontoPlanoContaAzulFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DescontoPlanoContaAzulFormService {
  createDescontoPlanoContaAzulFormGroup(
    descontoPlanoContaAzul: DescontoPlanoContaAzulFormGroupInput = { id: null },
  ): DescontoPlanoContaAzulFormGroup {
    const descontoPlanoContaAzulRawValue = {
      ...this.getFormDefaults(),
      ...descontoPlanoContaAzul,
    };
    return new FormGroup<DescontoPlanoContaAzulFormGroupContent>({
      id: new FormControl(
        { value: descontoPlanoContaAzulRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      percentual: new FormControl(descontoPlanoContaAzulRawValue.percentual),
      planoContaAzul: new FormControl(descontoPlanoContaAzulRawValue.planoContaAzul, {
        validators: [Validators.required],
      }),
      periodoPagamento: new FormControl(descontoPlanoContaAzulRawValue.periodoPagamento, {
        validators: [Validators.required],
      }),
    });
  }

  getDescontoPlanoContaAzul(form: DescontoPlanoContaAzulFormGroup): IDescontoPlanoContaAzul | NewDescontoPlanoContaAzul {
    return form.getRawValue() as IDescontoPlanoContaAzul | NewDescontoPlanoContaAzul;
  }

  resetForm(form: DescontoPlanoContaAzulFormGroup, descontoPlanoContaAzul: DescontoPlanoContaAzulFormGroupInput): void {
    const descontoPlanoContaAzulRawValue = { ...this.getFormDefaults(), ...descontoPlanoContaAzul };
    form.reset(
      {
        ...descontoPlanoContaAzulRawValue,
        id: { value: descontoPlanoContaAzulRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DescontoPlanoContaAzulFormDefaults {
    return {
      id: null,
    };
  }
}
