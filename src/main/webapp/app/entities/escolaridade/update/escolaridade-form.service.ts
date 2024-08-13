import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEscolaridade, NewEscolaridade } from '../escolaridade.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEscolaridade for edit and NewEscolaridadeFormGroupInput for create.
 */
type EscolaridadeFormGroupInput = IEscolaridade | PartialWithRequiredKeyOf<NewEscolaridade>;

type EscolaridadeFormDefaults = Pick<NewEscolaridade, 'id'>;

type EscolaridadeFormGroupContent = {
  id: FormControl<IEscolaridade['id'] | NewEscolaridade['id']>;
  nome: FormControl<IEscolaridade['nome']>;
  descricao: FormControl<IEscolaridade['descricao']>;
};

export type EscolaridadeFormGroup = FormGroup<EscolaridadeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EscolaridadeFormService {
  createEscolaridadeFormGroup(escolaridade: EscolaridadeFormGroupInput = { id: null }): EscolaridadeFormGroup {
    const escolaridadeRawValue = {
      ...this.getFormDefaults(),
      ...escolaridade,
    };
    return new FormGroup<EscolaridadeFormGroupContent>({
      id: new FormControl(
        { value: escolaridadeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(escolaridadeRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(escolaridadeRawValue.descricao),
    });
  }

  getEscolaridade(form: EscolaridadeFormGroup): IEscolaridade | NewEscolaridade {
    return form.getRawValue() as IEscolaridade | NewEscolaridade;
  }

  resetForm(form: EscolaridadeFormGroup, escolaridade: EscolaridadeFormGroupInput): void {
    const escolaridadeRawValue = { ...this.getFormDefaults(), ...escolaridade };
    form.reset(
      {
        ...escolaridadeRawValue,
        id: { value: escolaridadeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EscolaridadeFormDefaults {
    return {
      id: null,
    };
  }
}
