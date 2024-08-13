import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICriterioAvaliacao, NewCriterioAvaliacao } from '../criterio-avaliacao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICriterioAvaliacao for edit and NewCriterioAvaliacaoFormGroupInput for create.
 */
type CriterioAvaliacaoFormGroupInput = ICriterioAvaliacao | PartialWithRequiredKeyOf<NewCriterioAvaliacao>;

type CriterioAvaliacaoFormDefaults = Pick<NewCriterioAvaliacao, 'id'>;

type CriterioAvaliacaoFormGroupContent = {
  id: FormControl<ICriterioAvaliacao['id'] | NewCriterioAvaliacao['id']>;
  nome: FormControl<ICriterioAvaliacao['nome']>;
  descricao: FormControl<ICriterioAvaliacao['descricao']>;
};

export type CriterioAvaliacaoFormGroup = FormGroup<CriterioAvaliacaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CriterioAvaliacaoFormService {
  createCriterioAvaliacaoFormGroup(criterioAvaliacao: CriterioAvaliacaoFormGroupInput = { id: null }): CriterioAvaliacaoFormGroup {
    const criterioAvaliacaoRawValue = {
      ...this.getFormDefaults(),
      ...criterioAvaliacao,
    };
    return new FormGroup<CriterioAvaliacaoFormGroupContent>({
      id: new FormControl(
        { value: criterioAvaliacaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(criterioAvaliacaoRawValue.nome, {
        validators: [Validators.required],
      }),
      descricao: new FormControl(criterioAvaliacaoRawValue.descricao),
    });
  }

  getCriterioAvaliacao(form: CriterioAvaliacaoFormGroup): ICriterioAvaliacao | NewCriterioAvaliacao {
    return form.getRawValue() as ICriterioAvaliacao | NewCriterioAvaliacao;
  }

  resetForm(form: CriterioAvaliacaoFormGroup, criterioAvaliacao: CriterioAvaliacaoFormGroupInput): void {
    const criterioAvaliacaoRawValue = { ...this.getFormDefaults(), ...criterioAvaliacao };
    form.reset(
      {
        ...criterioAvaliacaoRawValue,
        id: { value: criterioAvaliacaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CriterioAvaliacaoFormDefaults {
    return {
      id: null,
    };
  }
}
