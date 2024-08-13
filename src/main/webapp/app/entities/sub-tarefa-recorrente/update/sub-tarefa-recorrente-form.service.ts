import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISubTarefaRecorrente, NewSubTarefaRecorrente } from '../sub-tarefa-recorrente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISubTarefaRecorrente for edit and NewSubTarefaRecorrenteFormGroupInput for create.
 */
type SubTarefaRecorrenteFormGroupInput = ISubTarefaRecorrente | PartialWithRequiredKeyOf<NewSubTarefaRecorrente>;

type SubTarefaRecorrenteFormDefaults = Pick<NewSubTarefaRecorrente, 'id' | 'concluida'>;

type SubTarefaRecorrenteFormGroupContent = {
  id: FormControl<ISubTarefaRecorrente['id'] | NewSubTarefaRecorrente['id']>;
  nome: FormControl<ISubTarefaRecorrente['nome']>;
  descricao: FormControl<ISubTarefaRecorrente['descricao']>;
  ordem: FormControl<ISubTarefaRecorrente['ordem']>;
  concluida: FormControl<ISubTarefaRecorrente['concluida']>;
  tarefaRecorrenteExecucao: FormControl<ISubTarefaRecorrente['tarefaRecorrenteExecucao']>;
};

export type SubTarefaRecorrenteFormGroup = FormGroup<SubTarefaRecorrenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SubTarefaRecorrenteFormService {
  createSubTarefaRecorrenteFormGroup(subTarefaRecorrente: SubTarefaRecorrenteFormGroupInput = { id: null }): SubTarefaRecorrenteFormGroup {
    const subTarefaRecorrenteRawValue = {
      ...this.getFormDefaults(),
      ...subTarefaRecorrente,
    };
    return new FormGroup<SubTarefaRecorrenteFormGroupContent>({
      id: new FormControl(
        { value: subTarefaRecorrenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(subTarefaRecorrenteRawValue.nome),
      descricao: new FormControl(subTarefaRecorrenteRawValue.descricao),
      ordem: new FormControl(subTarefaRecorrenteRawValue.ordem),
      concluida: new FormControl(subTarefaRecorrenteRawValue.concluida),
      tarefaRecorrenteExecucao: new FormControl(subTarefaRecorrenteRawValue.tarefaRecorrenteExecucao, {
        validators: [Validators.required],
      }),
    });
  }

  getSubTarefaRecorrente(form: SubTarefaRecorrenteFormGroup): ISubTarefaRecorrente | NewSubTarefaRecorrente {
    return form.getRawValue() as ISubTarefaRecorrente | NewSubTarefaRecorrente;
  }

  resetForm(form: SubTarefaRecorrenteFormGroup, subTarefaRecorrente: SubTarefaRecorrenteFormGroupInput): void {
    const subTarefaRecorrenteRawValue = { ...this.getFormDefaults(), ...subTarefaRecorrente };
    form.reset(
      {
        ...subTarefaRecorrenteRawValue,
        id: { value: subTarefaRecorrenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SubTarefaRecorrenteFormDefaults {
    return {
      id: null,
      concluida: false,
    };
  }
}
