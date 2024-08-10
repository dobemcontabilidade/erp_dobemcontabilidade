import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFuncionario, NewFuncionario } from '../funcionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuncionario for edit and NewFuncionarioFormGroupInput for create.
 */
type FuncionarioFormGroupInput = IFuncionario | PartialWithRequiredKeyOf<NewFuncionario>;

type FuncionarioFormDefaults = Pick<NewFuncionario, 'id'>;

type FuncionarioFormGroupContent = {
  id: FormControl<IFuncionario['id'] | NewFuncionario['id']>;
  nome: FormControl<IFuncionario['nome']>;
  salario: FormControl<IFuncionario['salario']>;
  ctps: FormControl<IFuncionario['ctps']>;
  cargo: FormControl<IFuncionario['cargo']>;
  descricaoAtividades: FormControl<IFuncionario['descricaoAtividades']>;
  situacao: FormControl<IFuncionario['situacao']>;
  pessoa: FormControl<IFuncionario['pessoa']>;
  empresa: FormControl<IFuncionario['empresa']>;
};

export type FuncionarioFormGroup = FormGroup<FuncionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuncionarioFormService {
  createFuncionarioFormGroup(funcionario: FuncionarioFormGroupInput = { id: null }): FuncionarioFormGroup {
    const funcionarioRawValue = {
      ...this.getFormDefaults(),
      ...funcionario,
    };
    return new FormGroup<FuncionarioFormGroupContent>({
      id: new FormControl(
        { value: funcionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(funcionarioRawValue.nome, {
        validators: [Validators.maxLength(200)],
      }),
      salario: new FormControl(funcionarioRawValue.salario),
      ctps: new FormControl(funcionarioRawValue.ctps),
      cargo: new FormControl(funcionarioRawValue.cargo),
      descricaoAtividades: new FormControl(funcionarioRawValue.descricaoAtividades),
      situacao: new FormControl(funcionarioRawValue.situacao),
      pessoa: new FormControl(funcionarioRawValue.pessoa, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(funcionarioRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getFuncionario(form: FuncionarioFormGroup): IFuncionario | NewFuncionario {
    return form.getRawValue() as IFuncionario | NewFuncionario;
  }

  resetForm(form: FuncionarioFormGroup, funcionario: FuncionarioFormGroupInput): void {
    const funcionarioRawValue = { ...this.getFormDefaults(), ...funcionario };
    form.reset(
      {
        ...funcionarioRawValue,
        id: { value: funcionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuncionarioFormDefaults {
    return {
      id: null,
    };
  }
}
