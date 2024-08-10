import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGrupoCnae, NewGrupoCnae } from '../grupo-cnae.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGrupoCnae for edit and NewGrupoCnaeFormGroupInput for create.
 */
type GrupoCnaeFormGroupInput = IGrupoCnae | PartialWithRequiredKeyOf<NewGrupoCnae>;

type GrupoCnaeFormDefaults = Pick<NewGrupoCnae, 'id'>;

type GrupoCnaeFormGroupContent = {
  id: FormControl<IGrupoCnae['id'] | NewGrupoCnae['id']>;
  codigo: FormControl<IGrupoCnae['codigo']>;
  descricao: FormControl<IGrupoCnae['descricao']>;
  divisao: FormControl<IGrupoCnae['divisao']>;
};

export type GrupoCnaeFormGroup = FormGroup<GrupoCnaeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GrupoCnaeFormService {
  createGrupoCnaeFormGroup(grupoCnae: GrupoCnaeFormGroupInput = { id: null }): GrupoCnaeFormGroup {
    const grupoCnaeRawValue = {
      ...this.getFormDefaults(),
      ...grupoCnae,
    };
    return new FormGroup<GrupoCnaeFormGroupContent>({
      id: new FormControl(
        { value: grupoCnaeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigo: new FormControl(grupoCnaeRawValue.codigo, {
        validators: [Validators.maxLength(15)],
      }),
      descricao: new FormControl(grupoCnaeRawValue.descricao, {
        validators: [Validators.required],
      }),
      divisao: new FormControl(grupoCnaeRawValue.divisao, {
        validators: [Validators.required],
      }),
    });
  }

  getGrupoCnae(form: GrupoCnaeFormGroup): IGrupoCnae | NewGrupoCnae {
    return form.getRawValue() as IGrupoCnae | NewGrupoCnae;
  }

  resetForm(form: GrupoCnaeFormGroup, grupoCnae: GrupoCnaeFormGroupInput): void {
    const grupoCnaeRawValue = { ...this.getFormDefaults(), ...grupoCnae };
    form.reset(
      {
        ...grupoCnaeRawValue,
        id: { value: grupoCnaeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GrupoCnaeFormDefaults {
    return {
      id: null,
    };
  }
}
