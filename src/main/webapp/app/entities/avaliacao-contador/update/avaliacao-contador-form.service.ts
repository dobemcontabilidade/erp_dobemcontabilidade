import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAvaliacaoContador, NewAvaliacaoContador } from '../avaliacao-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAvaliacaoContador for edit and NewAvaliacaoContadorFormGroupInput for create.
 */
type AvaliacaoContadorFormGroupInput = IAvaliacaoContador | PartialWithRequiredKeyOf<NewAvaliacaoContador>;

type AvaliacaoContadorFormDefaults = Pick<NewAvaliacaoContador, 'id'>;

type AvaliacaoContadorFormGroupContent = {
  id: FormControl<IAvaliacaoContador['id'] | NewAvaliacaoContador['id']>;
  pontuacao: FormControl<IAvaliacaoContador['pontuacao']>;
  contador: FormControl<IAvaliacaoContador['contador']>;
  avaliacao: FormControl<IAvaliacaoContador['avaliacao']>;
};

export type AvaliacaoContadorFormGroup = FormGroup<AvaliacaoContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AvaliacaoContadorFormService {
  createAvaliacaoContadorFormGroup(avaliacaoContador: AvaliacaoContadorFormGroupInput = { id: null }): AvaliacaoContadorFormGroup {
    const avaliacaoContadorRawValue = {
      ...this.getFormDefaults(),
      ...avaliacaoContador,
    };
    return new FormGroup<AvaliacaoContadorFormGroupContent>({
      id: new FormControl(
        { value: avaliacaoContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      pontuacao: new FormControl(avaliacaoContadorRawValue.pontuacao),
      contador: new FormControl(avaliacaoContadorRawValue.contador, {
        validators: [Validators.required],
      }),
      avaliacao: new FormControl(avaliacaoContadorRawValue.avaliacao, {
        validators: [Validators.required],
      }),
    });
  }

  getAvaliacaoContador(form: AvaliacaoContadorFormGroup): IAvaliacaoContador | NewAvaliacaoContador {
    return form.getRawValue() as IAvaliacaoContador | NewAvaliacaoContador;
  }

  resetForm(form: AvaliacaoContadorFormGroup, avaliacaoContador: AvaliacaoContadorFormGroupInput): void {
    const avaliacaoContadorRawValue = { ...this.getFormDefaults(), ...avaliacaoContador };
    form.reset(
      {
        ...avaliacaoContadorRawValue,
        id: { value: avaliacaoContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AvaliacaoContadorFormDefaults {
    return {
      id: null,
    };
  }
}
