import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICriterioAvaliacaoAtor, NewCriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICriterioAvaliacaoAtor for edit and NewCriterioAvaliacaoAtorFormGroupInput for create.
 */
type CriterioAvaliacaoAtorFormGroupInput = ICriterioAvaliacaoAtor | PartialWithRequiredKeyOf<NewCriterioAvaliacaoAtor>;

type CriterioAvaliacaoAtorFormDefaults = Pick<NewCriterioAvaliacaoAtor, 'id' | 'ativo'>;

type CriterioAvaliacaoAtorFormGroupContent = {
  id: FormControl<ICriterioAvaliacaoAtor['id'] | NewCriterioAvaliacaoAtor['id']>;
  descricao: FormControl<ICriterioAvaliacaoAtor['descricao']>;
  ativo: FormControl<ICriterioAvaliacaoAtor['ativo']>;
  criterioAvaliacao: FormControl<ICriterioAvaliacaoAtor['criterioAvaliacao']>;
  atorAvaliado: FormControl<ICriterioAvaliacaoAtor['atorAvaliado']>;
};

export type CriterioAvaliacaoAtorFormGroup = FormGroup<CriterioAvaliacaoAtorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CriterioAvaliacaoAtorFormService {
  createCriterioAvaliacaoAtorFormGroup(
    criterioAvaliacaoAtor: CriterioAvaliacaoAtorFormGroupInput = { id: null },
  ): CriterioAvaliacaoAtorFormGroup {
    const criterioAvaliacaoAtorRawValue = {
      ...this.getFormDefaults(),
      ...criterioAvaliacaoAtor,
    };
    return new FormGroup<CriterioAvaliacaoAtorFormGroupContent>({
      id: new FormControl(
        { value: criterioAvaliacaoAtorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      descricao: new FormControl(criterioAvaliacaoAtorRawValue.descricao),
      ativo: new FormControl(criterioAvaliacaoAtorRawValue.ativo),
      criterioAvaliacao: new FormControl(criterioAvaliacaoAtorRawValue.criterioAvaliacao, {
        validators: [Validators.required],
      }),
      atorAvaliado: new FormControl(criterioAvaliacaoAtorRawValue.atorAvaliado, {
        validators: [Validators.required],
      }),
    });
  }

  getCriterioAvaliacaoAtor(form: CriterioAvaliacaoAtorFormGroup): ICriterioAvaliacaoAtor | NewCriterioAvaliacaoAtor {
    return form.getRawValue() as ICriterioAvaliacaoAtor | NewCriterioAvaliacaoAtor;
  }

  resetForm(form: CriterioAvaliacaoAtorFormGroup, criterioAvaliacaoAtor: CriterioAvaliacaoAtorFormGroupInput): void {
    const criterioAvaliacaoAtorRawValue = { ...this.getFormDefaults(), ...criterioAvaliacaoAtor };
    form.reset(
      {
        ...criterioAvaliacaoAtorRawValue,
        id: { value: criterioAvaliacaoAtorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CriterioAvaliacaoAtorFormDefaults {
    return {
      id: null,
      ativo: false,
    };
  }
}
