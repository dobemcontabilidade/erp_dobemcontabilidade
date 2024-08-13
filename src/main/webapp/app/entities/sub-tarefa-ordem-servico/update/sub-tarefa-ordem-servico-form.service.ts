import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ISubTarefaOrdemServico, NewSubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ISubTarefaOrdemServico for edit and NewSubTarefaOrdemServicoFormGroupInput for create.
 */
type SubTarefaOrdemServicoFormGroupInput = ISubTarefaOrdemServico | PartialWithRequiredKeyOf<NewSubTarefaOrdemServico>;

type SubTarefaOrdemServicoFormDefaults = Pick<NewSubTarefaOrdemServico, 'id' | 'concluida'>;

type SubTarefaOrdemServicoFormGroupContent = {
  id: FormControl<ISubTarefaOrdemServico['id'] | NewSubTarefaOrdemServico['id']>;
  nome: FormControl<ISubTarefaOrdemServico['nome']>;
  descricao: FormControl<ISubTarefaOrdemServico['descricao']>;
  ordem: FormControl<ISubTarefaOrdemServico['ordem']>;
  concluida: FormControl<ISubTarefaOrdemServico['concluida']>;
  tarefaOrdemServicoExecucao: FormControl<ISubTarefaOrdemServico['tarefaOrdemServicoExecucao']>;
};

export type SubTarefaOrdemServicoFormGroup = FormGroup<SubTarefaOrdemServicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class SubTarefaOrdemServicoFormService {
  createSubTarefaOrdemServicoFormGroup(
    subTarefaOrdemServico: SubTarefaOrdemServicoFormGroupInput = { id: null },
  ): SubTarefaOrdemServicoFormGroup {
    const subTarefaOrdemServicoRawValue = {
      ...this.getFormDefaults(),
      ...subTarefaOrdemServico,
    };
    return new FormGroup<SubTarefaOrdemServicoFormGroupContent>({
      id: new FormControl(
        { value: subTarefaOrdemServicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(subTarefaOrdemServicoRawValue.nome),
      descricao: new FormControl(subTarefaOrdemServicoRawValue.descricao),
      ordem: new FormControl(subTarefaOrdemServicoRawValue.ordem),
      concluida: new FormControl(subTarefaOrdemServicoRawValue.concluida),
      tarefaOrdemServicoExecucao: new FormControl(subTarefaOrdemServicoRawValue.tarefaOrdemServicoExecucao, {
        validators: [Validators.required],
      }),
    });
  }

  getSubTarefaOrdemServico(form: SubTarefaOrdemServicoFormGroup): ISubTarefaOrdemServico | NewSubTarefaOrdemServico {
    return form.getRawValue() as ISubTarefaOrdemServico | NewSubTarefaOrdemServico;
  }

  resetForm(form: SubTarefaOrdemServicoFormGroup, subTarefaOrdemServico: SubTarefaOrdemServicoFormGroupInput): void {
    const subTarefaOrdemServicoRawValue = { ...this.getFormDefaults(), ...subTarefaOrdemServico };
    form.reset(
      {
        ...subTarefaOrdemServicoRawValue,
        id: { value: subTarefaOrdemServicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): SubTarefaOrdemServicoFormDefaults {
    return {
      id: null,
      concluida: false,
    };
  }
}
