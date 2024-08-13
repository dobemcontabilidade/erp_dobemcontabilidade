import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICompetencia, NewCompetencia } from '../competencia.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICompetencia for edit and NewCompetenciaFormGroupInput for create.
 */
type CompetenciaFormGroupInput = ICompetencia | PartialWithRequiredKeyOf<NewCompetencia>;

type CompetenciaFormDefaults = Pick<NewCompetencia, 'id'>;

type CompetenciaFormGroupContent = {
  id: FormControl<ICompetencia['id'] | NewCompetencia['id']>;
  nome: FormControl<ICompetencia['nome']>;
  numero: FormControl<ICompetencia['numero']>;
};

export type CompetenciaFormGroup = FormGroup<CompetenciaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CompetenciaFormService {
  createCompetenciaFormGroup(competencia: CompetenciaFormGroupInput = { id: null }): CompetenciaFormGroup {
    const competenciaRawValue = {
      ...this.getFormDefaults(),
      ...competencia,
    };
    return new FormGroup<CompetenciaFormGroupContent>({
      id: new FormControl(
        { value: competenciaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(competenciaRawValue.nome),
      numero: new FormControl(competenciaRawValue.numero),
    });
  }

  getCompetencia(form: CompetenciaFormGroup): ICompetencia | NewCompetencia {
    return form.getRawValue() as ICompetencia | NewCompetencia;
  }

  resetForm(form: CompetenciaFormGroup, competencia: CompetenciaFormGroupInput): void {
    const competenciaRawValue = { ...this.getFormDefaults(), ...competencia };
    form.reset(
      {
        ...competenciaRawValue,
        id: { value: competenciaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CompetenciaFormDefaults {
    return {
      id: null,
    };
  }
}
