import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlanoAssinaturaContabil, NewPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlanoAssinaturaContabil for edit and NewPlanoAssinaturaContabilFormGroupInput for create.
 */
type PlanoAssinaturaContabilFormGroupInput = IPlanoAssinaturaContabil | PartialWithRequiredKeyOf<NewPlanoAssinaturaContabil>;

type PlanoAssinaturaContabilFormDefaults = Pick<NewPlanoAssinaturaContabil, 'id'>;

type PlanoAssinaturaContabilFormGroupContent = {
  id: FormControl<IPlanoAssinaturaContabil['id'] | NewPlanoAssinaturaContabil['id']>;
  nome: FormControl<IPlanoAssinaturaContabil['nome']>;
  adicionalSocio: FormControl<IPlanoAssinaturaContabil['adicionalSocio']>;
  adicionalFuncionario: FormControl<IPlanoAssinaturaContabil['adicionalFuncionario']>;
  sociosIsentos: FormControl<IPlanoAssinaturaContabil['sociosIsentos']>;
  adicionalFaturamento: FormControl<IPlanoAssinaturaContabil['adicionalFaturamento']>;
  valorBaseFaturamento: FormControl<IPlanoAssinaturaContabil['valorBaseFaturamento']>;
  valorBaseAbertura: FormControl<IPlanoAssinaturaContabil['valorBaseAbertura']>;
  situacao: FormControl<IPlanoAssinaturaContabil['situacao']>;
};

export type PlanoAssinaturaContabilFormGroup = FormGroup<PlanoAssinaturaContabilFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlanoAssinaturaContabilFormService {
  createPlanoAssinaturaContabilFormGroup(
    planoAssinaturaContabil: PlanoAssinaturaContabilFormGroupInput = { id: null },
  ): PlanoAssinaturaContabilFormGroup {
    const planoAssinaturaContabilRawValue = {
      ...this.getFormDefaults(),
      ...planoAssinaturaContabil,
    };
    return new FormGroup<PlanoAssinaturaContabilFormGroupContent>({
      id: new FormControl(
        { value: planoAssinaturaContabilRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(planoAssinaturaContabilRawValue.nome),
      adicionalSocio: new FormControl(planoAssinaturaContabilRawValue.adicionalSocio),
      adicionalFuncionario: new FormControl(planoAssinaturaContabilRawValue.adicionalFuncionario),
      sociosIsentos: new FormControl(planoAssinaturaContabilRawValue.sociosIsentos),
      adicionalFaturamento: new FormControl(planoAssinaturaContabilRawValue.adicionalFaturamento),
      valorBaseFaturamento: new FormControl(planoAssinaturaContabilRawValue.valorBaseFaturamento),
      valorBaseAbertura: new FormControl(planoAssinaturaContabilRawValue.valorBaseAbertura),
      situacao: new FormControl(planoAssinaturaContabilRawValue.situacao),
    });
  }

  getPlanoAssinaturaContabil(form: PlanoAssinaturaContabilFormGroup): IPlanoAssinaturaContabil | NewPlanoAssinaturaContabil {
    return form.getRawValue() as IPlanoAssinaturaContabil | NewPlanoAssinaturaContabil;
  }

  resetForm(form: PlanoAssinaturaContabilFormGroup, planoAssinaturaContabil: PlanoAssinaturaContabilFormGroupInput): void {
    const planoAssinaturaContabilRawValue = { ...this.getFormDefaults(), ...planoAssinaturaContabil };
    form.reset(
      {
        ...planoAssinaturaContabilRawValue,
        id: { value: planoAssinaturaContabilRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PlanoAssinaturaContabilFormDefaults {
    return {
      id: null,
    };
  }
}
