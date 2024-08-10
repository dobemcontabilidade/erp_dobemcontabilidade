import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDepartamento, NewDepartamento } from '../departamento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDepartamento for edit and NewDepartamentoFormGroupInput for create.
 */
type DepartamentoFormGroupInput = IDepartamento | PartialWithRequiredKeyOf<NewDepartamento>;

type DepartamentoFormDefaults = Pick<NewDepartamento, 'id'>;

type DepartamentoFormGroupContent = {
  id: FormControl<IDepartamento['id'] | NewDepartamento['id']>;
  nome: FormControl<IDepartamento['nome']>;
  descricao: FormControl<IDepartamento['descricao']>;
};

export type DepartamentoFormGroup = FormGroup<DepartamentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DepartamentoFormService {
  createDepartamentoFormGroup(departamento: DepartamentoFormGroupInput = { id: null }): DepartamentoFormGroup {
    const departamentoRawValue = {
      ...this.getFormDefaults(),
      ...departamento,
    };
    return new FormGroup<DepartamentoFormGroupContent>({
      id: new FormControl(
        { value: departamentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(departamentoRawValue.nome),
      descricao: new FormControl(departamentoRawValue.descricao),
    });
  }

  getDepartamento(form: DepartamentoFormGroup): IDepartamento | NewDepartamento {
    return form.getRawValue() as IDepartamento | NewDepartamento;
  }

  resetForm(form: DepartamentoFormGroup, departamento: DepartamentoFormGroupInput): void {
    const departamentoRawValue = { ...this.getFormDefaults(), ...departamento };
    form.reset(
      {
        ...departamentoRawValue,
        id: { value: departamentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DepartamentoFormDefaults {
    return {
      id: null,
    };
  }
}
