import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDependentesFuncionario, NewDependentesFuncionario } from '../dependentes-funcionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDependentesFuncionario for edit and NewDependentesFuncionarioFormGroupInput for create.
 */
type DependentesFuncionarioFormGroupInput = IDependentesFuncionario | PartialWithRequiredKeyOf<NewDependentesFuncionario>;

type DependentesFuncionarioFormDefaults = Pick<NewDependentesFuncionario, 'id' | 'dependenteIRRF' | 'dependenteSalarioFamilia'>;

type DependentesFuncionarioFormGroupContent = {
  id: FormControl<IDependentesFuncionario['id'] | NewDependentesFuncionario['id']>;
  urlCertidaoDependente: FormControl<IDependentesFuncionario['urlCertidaoDependente']>;
  urlRgDependente: FormControl<IDependentesFuncionario['urlRgDependente']>;
  dependenteIRRF: FormControl<IDependentesFuncionario['dependenteIRRF']>;
  dependenteSalarioFamilia: FormControl<IDependentesFuncionario['dependenteSalarioFamilia']>;
  tipoDependenteFuncionarioEnum: FormControl<IDependentesFuncionario['tipoDependenteFuncionarioEnum']>;
  pessoa: FormControl<IDependentesFuncionario['pessoa']>;
  funcionario: FormControl<IDependentesFuncionario['funcionario']>;
};

export type DependentesFuncionarioFormGroup = FormGroup<DependentesFuncionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DependentesFuncionarioFormService {
  createDependentesFuncionarioFormGroup(
    dependentesFuncionario: DependentesFuncionarioFormGroupInput = { id: null },
  ): DependentesFuncionarioFormGroup {
    const dependentesFuncionarioRawValue = {
      ...this.getFormDefaults(),
      ...dependentesFuncionario,
    };
    return new FormGroup<DependentesFuncionarioFormGroupContent>({
      id: new FormControl(
        { value: dependentesFuncionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      urlCertidaoDependente: new FormControl(dependentesFuncionarioRawValue.urlCertidaoDependente),
      urlRgDependente: new FormControl(dependentesFuncionarioRawValue.urlRgDependente),
      dependenteIRRF: new FormControl(dependentesFuncionarioRawValue.dependenteIRRF),
      dependenteSalarioFamilia: new FormControl(dependentesFuncionarioRawValue.dependenteSalarioFamilia),
      tipoDependenteFuncionarioEnum: new FormControl(dependentesFuncionarioRawValue.tipoDependenteFuncionarioEnum),
      pessoa: new FormControl(dependentesFuncionarioRawValue.pessoa, {
        validators: [Validators.required],
      }),
      funcionario: new FormControl(dependentesFuncionarioRawValue.funcionario, {
        validators: [Validators.required],
      }),
    });
  }

  getDependentesFuncionario(form: DependentesFuncionarioFormGroup): IDependentesFuncionario | NewDependentesFuncionario {
    return form.getRawValue() as IDependentesFuncionario | NewDependentesFuncionario;
  }

  resetForm(form: DependentesFuncionarioFormGroup, dependentesFuncionario: DependentesFuncionarioFormGroupInput): void {
    const dependentesFuncionarioRawValue = { ...this.getFormDefaults(), ...dependentesFuncionario };
    form.reset(
      {
        ...dependentesFuncionarioRawValue,
        id: { value: dependentesFuncionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DependentesFuncionarioFormDefaults {
    return {
      id: null,
      dependenteIRRF: false,
      dependenteSalarioFamilia: false,
    };
  }
}
