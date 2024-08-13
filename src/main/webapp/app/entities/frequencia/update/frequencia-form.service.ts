import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFrequencia, NewFrequencia } from '../frequencia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFrequencia for edit and NewFrequenciaFormGroupInput for create.
 */
type FrequenciaFormGroupInput = IFrequencia | PartialWithRequiredKeyOf<NewFrequencia>;

type FrequenciaFormDefaults = Pick<NewFrequencia, 'id'>;

type FrequenciaFormGroupContent = {
  id: FormControl<IFrequencia['id'] | NewFrequencia['id']>;
  nome: FormControl<IFrequencia['nome']>;
  descricao: FormControl<IFrequencia['descricao']>;
  numeroDias: FormControl<IFrequencia['numeroDias']>;
};

export type FrequenciaFormGroup = FormGroup<FrequenciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FrequenciaFormService {
  createFrequenciaFormGroup(frequencia: FrequenciaFormGroupInput = { id: null }): FrequenciaFormGroup {
    const frequenciaRawValue = {
      ...this.getFormDefaults(),
      ...frequencia,
    };
    return new FormGroup<FrequenciaFormGroupContent>({
      id: new FormControl(
        { value: frequenciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(frequenciaRawValue.nome),
      descricao: new FormControl(frequenciaRawValue.descricao),
      numeroDias: new FormControl(frequenciaRawValue.numeroDias),
    });
  }

  getFrequencia(form: FrequenciaFormGroup): IFrequencia | NewFrequencia {
    return form.getRawValue() as IFrequencia | NewFrequencia;
  }

  resetForm(form: FrequenciaFormGroup, frequencia: FrequenciaFormGroupInput): void {
    const frequenciaRawValue = { ...this.getFormDefaults(), ...frequencia };
    form.reset(
      {
        ...frequenciaRawValue,
        id: { value: frequenciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FrequenciaFormDefaults {
    return {
      id: null,
    };
  }
}
