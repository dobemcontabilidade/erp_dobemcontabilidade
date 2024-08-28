import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAdicionalRamo, NewAdicionalRamo } from '../adicional-ramo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAdicionalRamo for edit and NewAdicionalRamoFormGroupInput for create.
 */
type AdicionalRamoFormGroupInput = IAdicionalRamo | PartialWithRequiredKeyOf<NewAdicionalRamo>;

type AdicionalRamoFormDefaults = Pick<NewAdicionalRamo, 'id'>;

type AdicionalRamoFormGroupContent = {
  id: FormControl<IAdicionalRamo['id'] | NewAdicionalRamo['id']>;
  valor: FormControl<IAdicionalRamo['valor']>;
  ramo: FormControl<IAdicionalRamo['ramo']>;
  planoAssinaturaContabil: FormControl<IAdicionalRamo['planoAssinaturaContabil']>;
};

export type AdicionalRamoFormGroup = FormGroup<AdicionalRamoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AdicionalRamoFormService {
  createAdicionalRamoFormGroup(adicionalRamo: AdicionalRamoFormGroupInput = { id: null }): AdicionalRamoFormGroup {
    const adicionalRamoRawValue = {
      ...this.getFormDefaults(),
      ...adicionalRamo,
    };
    return new FormGroup<AdicionalRamoFormGroupContent>({
      id: new FormControl(
        { value: adicionalRamoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valor: new FormControl(adicionalRamoRawValue.valor, {
        validators: [Validators.required],
      }),
      ramo: new FormControl(adicionalRamoRawValue.ramo, {
        validators: [Validators.required],
      }),
      planoAssinaturaContabil: new FormControl(adicionalRamoRawValue.planoAssinaturaContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getAdicionalRamo(form: AdicionalRamoFormGroup): IAdicionalRamo | NewAdicionalRamo {
    return form.getRawValue() as IAdicionalRamo | NewAdicionalRamo;
  }

  resetForm(form: AdicionalRamoFormGroup, adicionalRamo: AdicionalRamoFormGroupInput): void {
    const adicionalRamoRawValue = { ...this.getFormDefaults(), ...adicionalRamo };
    form.reset(
      {
        ...adicionalRamoRawValue,
        id: { value: adicionalRamoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AdicionalRamoFormDefaults {
    return {
      id: null,
    };
  }
}
