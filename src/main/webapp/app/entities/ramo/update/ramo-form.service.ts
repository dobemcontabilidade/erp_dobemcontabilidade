import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IRamo, NewRamo } from '../ramo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IRamo for edit and NewRamoFormGroupInput for create.
 */
type RamoFormGroupInput = IRamo | PartialWithRequiredKeyOf<NewRamo>;

type RamoFormDefaults = Pick<NewRamo, 'id'>;

type RamoFormGroupContent = {
  id: FormControl<IRamo['id'] | NewRamo['id']>;
  nome: FormControl<IRamo['nome']>;
  descricao: FormControl<IRamo['descricao']>;
};

export type RamoFormGroup = FormGroup<RamoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class RamoFormService {
  createRamoFormGroup(ramo: RamoFormGroupInput = { id: null }): RamoFormGroup {
    const ramoRawValue = {
      ...this.getFormDefaults(),
      ...ramo,
    };
    return new FormGroup<RamoFormGroupContent>({
      id: new FormControl(
        { value: ramoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(ramoRawValue.nome, {
        validators: [Validators.maxLength(20)],
      }),
      descricao: new FormControl(ramoRawValue.descricao),
    });
  }

  getRamo(form: RamoFormGroup): IRamo | NewRamo {
    return form.getRawValue() as IRamo | NewRamo;
  }

  resetForm(form: RamoFormGroup, ramo: RamoFormGroupInput): void {
    const ramoRawValue = { ...this.getFormDefaults(), ...ramo };
    form.reset(
      {
        ...ramoRawValue,
        id: { value: ramoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): RamoFormDefaults {
    return {
      id: null,
    };
  }
}
