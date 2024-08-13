import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEsfera, NewEsfera } from '../esfera.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEsfera for edit and NewEsferaFormGroupInput for create.
 */
type EsferaFormGroupInput = IEsfera | PartialWithRequiredKeyOf<NewEsfera>;

type EsferaFormDefaults = Pick<NewEsfera, 'id'>;

type EsferaFormGroupContent = {
  id: FormControl<IEsfera['id'] | NewEsfera['id']>;
  nome: FormControl<IEsfera['nome']>;
  descricao: FormControl<IEsfera['descricao']>;
};

export type EsferaFormGroup = FormGroup<EsferaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EsferaFormService {
  createEsferaFormGroup(esfera: EsferaFormGroupInput = { id: null }): EsferaFormGroup {
    const esferaRawValue = {
      ...this.getFormDefaults(),
      ...esfera,
    };
    return new FormGroup<EsferaFormGroupContent>({
      id: new FormControl(
        { value: esferaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(esferaRawValue.nome, {
        validators: [Validators.maxLength(50)],
      }),
      descricao: new FormControl(esferaRawValue.descricao),
    });
  }

  getEsfera(form: EsferaFormGroup): IEsfera | NewEsfera {
    return form.getRawValue() as IEsfera | NewEsfera;
  }

  resetForm(form: EsferaFormGroup, esfera: EsferaFormGroupInput): void {
    const esferaRawValue = { ...this.getFormDefaults(), ...esfera };
    form.reset(
      {
        ...esferaRawValue,
        id: { value: esferaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EsferaFormDefaults {
    return {
      id: null,
    };
  }
}
