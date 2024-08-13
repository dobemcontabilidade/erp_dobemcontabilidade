import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFuncionalidade, NewFuncionalidade } from '../funcionalidade.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuncionalidade for edit and NewFuncionalidadeFormGroupInput for create.
 */
type FuncionalidadeFormGroupInput = IFuncionalidade | PartialWithRequiredKeyOf<NewFuncionalidade>;

type FuncionalidadeFormDefaults = Pick<NewFuncionalidade, 'id' | 'ativa'>;

type FuncionalidadeFormGroupContent = {
  id: FormControl<IFuncionalidade['id'] | NewFuncionalidade['id']>;
  nome: FormControl<IFuncionalidade['nome']>;
  ativa: FormControl<IFuncionalidade['ativa']>;
  modulo: FormControl<IFuncionalidade['modulo']>;
};

export type FuncionalidadeFormGroup = FormGroup<FuncionalidadeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuncionalidadeFormService {
  createFuncionalidadeFormGroup(funcionalidade: FuncionalidadeFormGroupInput = { id: null }): FuncionalidadeFormGroup {
    const funcionalidadeRawValue = {
      ...this.getFormDefaults(),
      ...funcionalidade,
    };
    return new FormGroup<FuncionalidadeFormGroupContent>({
      id: new FormControl(
        { value: funcionalidadeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(funcionalidadeRawValue.nome),
      ativa: new FormControl(funcionalidadeRawValue.ativa),
      modulo: new FormControl(funcionalidadeRawValue.modulo, {
        validators: [Validators.required],
      }),
    });
  }

  getFuncionalidade(form: FuncionalidadeFormGroup): IFuncionalidade | NewFuncionalidade {
    return form.getRawValue() as IFuncionalidade | NewFuncionalidade;
  }

  resetForm(form: FuncionalidadeFormGroup, funcionalidade: FuncionalidadeFormGroupInput): void {
    const funcionalidadeRawValue = { ...this.getFormDefaults(), ...funcionalidade };
    form.reset(
      {
        ...funcionalidadeRawValue,
        id: { value: funcionalidadeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuncionalidadeFormDefaults {
    return {
      id: null,
      ativa: false,
    };
  }
}
