import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlanoContabil, NewPlanoContabil } from '../plano-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanoContabil for edit and NewPlanoContabilFormGroupInput for create.
 */
type PlanoContabilFormGroupInput = IPlanoContabil | PartialWithRequiredKeyOf<NewPlanoContabil>;

type PlanoContabilFormDefaults = Pick<NewPlanoContabil, 'id'>;

type PlanoContabilFormGroupContent = {
  id: FormControl<IPlanoContabil['id'] | NewPlanoContabil['id']>;
  nome: FormControl<IPlanoContabil['nome']>;
  adicionalSocio: FormControl<IPlanoContabil['adicionalSocio']>;
  adicionalFuncionario: FormControl<IPlanoContabil['adicionalFuncionario']>;
  sociosIsentos: FormControl<IPlanoContabil['sociosIsentos']>;
  adicionalFaturamento: FormControl<IPlanoContabil['adicionalFaturamento']>;
  valorBaseFaturamento: FormControl<IPlanoContabil['valorBaseFaturamento']>;
  valorBaseAbertura: FormControl<IPlanoContabil['valorBaseAbertura']>;
  situacao: FormControl<IPlanoContabil['situacao']>;
};

export type PlanoContabilFormGroup = FormGroup<PlanoContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanoContabilFormService {
  createPlanoContabilFormGroup(planoContabil: PlanoContabilFormGroupInput = { id: null }): PlanoContabilFormGroup {
    const planoContabilRawValue = {
      ...this.getFormDefaults(),
      ...planoContabil,
    };
    return new FormGroup<PlanoContabilFormGroupContent>({
      id: new FormControl(
        { value: planoContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(planoContabilRawValue.nome),
      adicionalSocio: new FormControl(planoContabilRawValue.adicionalSocio),
      adicionalFuncionario: new FormControl(planoContabilRawValue.adicionalFuncionario),
      sociosIsentos: new FormControl(planoContabilRawValue.sociosIsentos),
      adicionalFaturamento: new FormControl(planoContabilRawValue.adicionalFaturamento),
      valorBaseFaturamento: new FormControl(planoContabilRawValue.valorBaseFaturamento),
      valorBaseAbertura: new FormControl(planoContabilRawValue.valorBaseAbertura),
      situacao: new FormControl(planoContabilRawValue.situacao),
    });
  }

  getPlanoContabil(form: PlanoContabilFormGroup): IPlanoContabil | NewPlanoContabil {
    return form.getRawValue() as IPlanoContabil | NewPlanoContabil;
  }

  resetForm(form: PlanoContabilFormGroup, planoContabil: PlanoContabilFormGroupInput): void {
    const planoContabilRawValue = { ...this.getFormDefaults(), ...planoContabil };
    form.reset(
      {
        ...planoContabilRawValue,
        id: { value: planoContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlanoContabilFormDefaults {
    return {
      id: null,
    };
  }
}
