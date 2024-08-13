import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IValorBaseRamo, NewValorBaseRamo } from '../valor-base-ramo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IValorBaseRamo for edit and NewValorBaseRamoFormGroupInput for create.
 */
type ValorBaseRamoFormGroupInput = IValorBaseRamo | PartialWithRequiredKeyOf<NewValorBaseRamo>;

type ValorBaseRamoFormDefaults = Pick<NewValorBaseRamo, 'id'>;

type ValorBaseRamoFormGroupContent = {
  id: FormControl<IValorBaseRamo['id'] | NewValorBaseRamo['id']>;
  valorBase: FormControl<IValorBaseRamo['valorBase']>;
  ramo: FormControl<IValorBaseRamo['ramo']>;
  planoContabil: FormControl<IValorBaseRamo['planoContabil']>;
};

export type ValorBaseRamoFormGroup = FormGroup<ValorBaseRamoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ValorBaseRamoFormService {
  createValorBaseRamoFormGroup(valorBaseRamo: ValorBaseRamoFormGroupInput = { id: null }): ValorBaseRamoFormGroup {
    const valorBaseRamoRawValue = {
      ...this.getFormDefaults(),
      ...valorBaseRamo,
    };
    return new FormGroup<ValorBaseRamoFormGroupContent>({
      id: new FormControl(
        { value: valorBaseRamoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valorBase: new FormControl(valorBaseRamoRawValue.valorBase, {
        validators: [Validators.required],
      }),
      ramo: new FormControl(valorBaseRamoRawValue.ramo, {
        validators: [Validators.required],
      }),
      planoContabil: new FormControl(valorBaseRamoRawValue.planoContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getValorBaseRamo(form: ValorBaseRamoFormGroup): IValorBaseRamo | NewValorBaseRamo {
    return form.getRawValue() as IValorBaseRamo | NewValorBaseRamo;
  }

  resetForm(form: ValorBaseRamoFormGroup, valorBaseRamo: ValorBaseRamoFormGroupInput): void {
    const valorBaseRamoRawValue = { ...this.getFormDefaults(), ...valorBaseRamo };
    form.reset(
      {
        ...valorBaseRamoRawValue,
        id: { value: valorBaseRamoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ValorBaseRamoFormDefaults {
    return {
      id: null,
    };
  }
}
