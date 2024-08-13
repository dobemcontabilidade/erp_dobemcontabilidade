import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDepartamentoContador, NewDepartamentoContador } from '../departamento-contador.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDepartamentoContador for edit and NewDepartamentoContadorFormGroupInput for create.
 */
type DepartamentoContadorFormGroupInput = IDepartamentoContador | PartialWithRequiredKeyOf<NewDepartamentoContador>;

type DepartamentoContadorFormDefaults = Pick<NewDepartamentoContador, 'id'>;

type DepartamentoContadorFormGroupContent = {
  id: FormControl<IDepartamentoContador['id'] | NewDepartamentoContador['id']>;
  percentualExperiencia: FormControl<IDepartamentoContador['percentualExperiencia']>;
  descricaoExperiencia: FormControl<IDepartamentoContador['descricaoExperiencia']>;
  pontuacaoEntrevista: FormControl<IDepartamentoContador['pontuacaoEntrevista']>;
  pontuacaoAvaliacao: FormControl<IDepartamentoContador['pontuacaoAvaliacao']>;
  departamento: FormControl<IDepartamentoContador['departamento']>;
  contador: FormControl<IDepartamentoContador['contador']>;
};

export type DepartamentoContadorFormGroup = FormGroup<DepartamentoContadorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DepartamentoContadorFormService {
  createDepartamentoContadorFormGroup(
    departamentoContador: DepartamentoContadorFormGroupInput = { id: null },
  ): DepartamentoContadorFormGroup {
    const departamentoContadorRawValue = {
      ...this.getFormDefaults(),
      ...departamentoContador,
    };
    return new FormGroup<DepartamentoContadorFormGroupContent>({
      id: new FormControl(
        { value: departamentoContadorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      percentualExperiencia: new FormControl(departamentoContadorRawValue.percentualExperiencia),
      descricaoExperiencia: new FormControl(departamentoContadorRawValue.descricaoExperiencia),
      pontuacaoEntrevista: new FormControl(departamentoContadorRawValue.pontuacaoEntrevista),
      pontuacaoAvaliacao: new FormControl(departamentoContadorRawValue.pontuacaoAvaliacao),
      departamento: new FormControl(departamentoContadorRawValue.departamento, {
        validators: [Validators.required],
      }),
      contador: new FormControl(departamentoContadorRawValue.contador, {
        validators: [Validators.required],
      }),
    });
  }

  getDepartamentoContador(form: DepartamentoContadorFormGroup): IDepartamentoContador | NewDepartamentoContador {
    return form.getRawValue() as IDepartamentoContador | NewDepartamentoContador;
  }

  resetForm(form: DepartamentoContadorFormGroup, departamentoContador: DepartamentoContadorFormGroupInput): void {
    const departamentoContadorRawValue = { ...this.getFormDefaults(), ...departamentoContador };
    form.reset(
      {
        ...departamentoContadorRawValue,
        id: { value: departamentoContadorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DepartamentoContadorFormDefaults {
    return {
      id: null,
    };
  }
}
