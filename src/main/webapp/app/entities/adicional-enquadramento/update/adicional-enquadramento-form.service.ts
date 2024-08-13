import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAdicionalEnquadramento, NewAdicionalEnquadramento } from '../adicional-enquadramento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAdicionalEnquadramento for edit and NewAdicionalEnquadramentoFormGroupInput for create.
 */
type AdicionalEnquadramentoFormGroupInput = IAdicionalEnquadramento | PartialWithRequiredKeyOf<NewAdicionalEnquadramento>;

type AdicionalEnquadramentoFormDefaults = Pick<NewAdicionalEnquadramento, 'id'>;

type AdicionalEnquadramentoFormGroupContent = {
  id: FormControl<IAdicionalEnquadramento['id'] | NewAdicionalEnquadramento['id']>;
  valor: FormControl<IAdicionalEnquadramento['valor']>;
  enquadramento: FormControl<IAdicionalEnquadramento['enquadramento']>;
  planoContabil: FormControl<IAdicionalEnquadramento['planoContabil']>;
};

export type AdicionalEnquadramentoFormGroup = FormGroup<AdicionalEnquadramentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AdicionalEnquadramentoFormService {
  createAdicionalEnquadramentoFormGroup(
    adicionalEnquadramento: AdicionalEnquadramentoFormGroupInput = { id: null },
  ): AdicionalEnquadramentoFormGroup {
    const adicionalEnquadramentoRawValue = {
      ...this.getFormDefaults(),
      ...adicionalEnquadramento,
    };
    return new FormGroup<AdicionalEnquadramentoFormGroupContent>({
      id: new FormControl(
        { value: adicionalEnquadramentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valor: new FormControl(adicionalEnquadramentoRawValue.valor),
      enquadramento: new FormControl(adicionalEnquadramentoRawValue.enquadramento, {
        validators: [Validators.required],
      }),
      planoContabil: new FormControl(adicionalEnquadramentoRawValue.planoContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getAdicionalEnquadramento(form: AdicionalEnquadramentoFormGroup): IAdicionalEnquadramento | NewAdicionalEnquadramento {
    return form.getRawValue() as IAdicionalEnquadramento | NewAdicionalEnquadramento;
  }

  resetForm(form: AdicionalEnquadramentoFormGroup, adicionalEnquadramento: AdicionalEnquadramentoFormGroupInput): void {
    const adicionalEnquadramentoRawValue = { ...this.getFormDefaults(), ...adicionalEnquadramento };
    form.reset(
      {
        ...adicionalEnquadramentoRawValue,
        id: { value: adicionalEnquadramentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AdicionalEnquadramentoFormDefaults {
    return {
      id: null,
    };
  }
}
