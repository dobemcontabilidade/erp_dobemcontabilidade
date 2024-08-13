import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ITelefone, NewTelefone } from '../telefone.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITelefone for edit and NewTelefoneFormGroupInput for create.
 */
type TelefoneFormGroupInput = ITelefone | PartialWithRequiredKeyOf<NewTelefone>;

type TelefoneFormDefaults = Pick<NewTelefone, 'id' | 'principal'>;

type TelefoneFormGroupContent = {
  id: FormControl<ITelefone['id'] | NewTelefone['id']>;
  codigoArea: FormControl<ITelefone['codigoArea']>;
  telefone: FormControl<ITelefone['telefone']>;
  principal: FormControl<ITelefone['principal']>;
  tipoTelefone: FormControl<ITelefone['tipoTelefone']>;
  pessoa: FormControl<ITelefone['pessoa']>;
};

export type TelefoneFormGroup = FormGroup<TelefoneFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TelefoneFormService {
  createTelefoneFormGroup(telefone: TelefoneFormGroupInput = { id: null }): TelefoneFormGroup {
    const telefoneRawValue = {
      ...this.getFormDefaults(),
      ...telefone,
    };
    return new FormGroup<TelefoneFormGroupContent>({
      id: new FormControl(
        { value: telefoneRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigoArea: new FormControl(telefoneRawValue.codigoArea, {
        validators: [Validators.required, Validators.maxLength(3)],
      }),
      telefone: new FormControl(telefoneRawValue.telefone, {
        validators: [Validators.required],
      }),
      principal: new FormControl(telefoneRawValue.principal),
      tipoTelefone: new FormControl(telefoneRawValue.tipoTelefone),
      pessoa: new FormControl(telefoneRawValue.pessoa, {
        validators: [Validators.required],
      }),
    });
  }

  getTelefone(form: TelefoneFormGroup): ITelefone | NewTelefone {
    return form.getRawValue() as ITelefone | NewTelefone;
  }

  resetForm(form: TelefoneFormGroup, telefone: TelefoneFormGroupInput): void {
    const telefoneRawValue = { ...this.getFormDefaults(), ...telefone };
    form.reset(
      {
        ...telefoneRawValue,
        id: { value: telefoneRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TelefoneFormDefaults {
    return {
      id: null,
      principal: false,
    };
  }
}
