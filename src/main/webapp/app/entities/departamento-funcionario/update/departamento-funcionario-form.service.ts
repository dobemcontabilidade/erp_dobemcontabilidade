import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDepartamentoFuncionario, NewDepartamentoFuncionario } from '../departamento-funcionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDepartamentoFuncionario for edit and NewDepartamentoFuncionarioFormGroupInput for create.
 */
type DepartamentoFuncionarioFormGroupInput = IDepartamentoFuncionario | PartialWithRequiredKeyOf<NewDepartamentoFuncionario>;

type DepartamentoFuncionarioFormDefaults = Pick<NewDepartamentoFuncionario, 'id'>;

type DepartamentoFuncionarioFormGroupContent = {
  id: FormControl<IDepartamentoFuncionario['id'] | NewDepartamentoFuncionario['id']>;
  cargo: FormControl<IDepartamentoFuncionario['cargo']>;
  funcionario: FormControl<IDepartamentoFuncionario['funcionario']>;
  departamento: FormControl<IDepartamentoFuncionario['departamento']>;
};

export type DepartamentoFuncionarioFormGroup = FormGroup<DepartamentoFuncionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DepartamentoFuncionarioFormService {
  createDepartamentoFuncionarioFormGroup(
    departamentoFuncionario: DepartamentoFuncionarioFormGroupInput = { id: null },
  ): DepartamentoFuncionarioFormGroup {
    const departamentoFuncionarioRawValue = {
      ...this.getFormDefaults(),
      ...departamentoFuncionario,
    };
    return new FormGroup<DepartamentoFuncionarioFormGroupContent>({
      id: new FormControl(
        { value: departamentoFuncionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cargo: new FormControl(departamentoFuncionarioRawValue.cargo, {
        validators: [Validators.required],
      }),
      funcionario: new FormControl(departamentoFuncionarioRawValue.funcionario, {
        validators: [Validators.required],
      }),
      departamento: new FormControl(departamentoFuncionarioRawValue.departamento, {
        validators: [Validators.required],
      }),
    });
  }

  getDepartamentoFuncionario(form: DepartamentoFuncionarioFormGroup): IDepartamentoFuncionario | NewDepartamentoFuncionario {
    return form.getRawValue() as IDepartamentoFuncionario | NewDepartamentoFuncionario;
  }

  resetForm(form: DepartamentoFuncionarioFormGroup, departamentoFuncionario: DepartamentoFuncionarioFormGroupInput): void {
    const departamentoFuncionarioRawValue = { ...this.getFormDefaults(), ...departamentoFuncionario };
    form.reset(
      {
        ...departamentoFuncionarioRawValue,
        id: { value: departamentoFuncionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DepartamentoFuncionarioFormDefaults {
    return {
      id: null,
    };
  }
}
