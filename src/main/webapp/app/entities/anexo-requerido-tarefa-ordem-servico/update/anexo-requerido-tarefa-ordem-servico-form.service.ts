import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAnexoRequeridoTarefaOrdemServico, NewAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoRequeridoTarefaOrdemServico for edit and NewAnexoRequeridoTarefaOrdemServicoFormGroupInput for create.
 */
type AnexoRequeridoTarefaOrdemServicoFormGroupInput =
  | IAnexoRequeridoTarefaOrdemServico
  | PartialWithRequiredKeyOf<NewAnexoRequeridoTarefaOrdemServico>;

type AnexoRequeridoTarefaOrdemServicoFormDefaults = Pick<NewAnexoRequeridoTarefaOrdemServico, 'id' | 'obrigatorio'>;

type AnexoRequeridoTarefaOrdemServicoFormGroupContent = {
  id: FormControl<IAnexoRequeridoTarefaOrdemServico['id'] | NewAnexoRequeridoTarefaOrdemServico['id']>;
  obrigatorio: FormControl<IAnexoRequeridoTarefaOrdemServico['obrigatorio']>;
  anexoRequerido: FormControl<IAnexoRequeridoTarefaOrdemServico['anexoRequerido']>;
  tarefaOrdemServico: FormControl<IAnexoRequeridoTarefaOrdemServico['tarefaOrdemServico']>;
};

export type AnexoRequeridoTarefaOrdemServicoFormGroup = FormGroup<AnexoRequeridoTarefaOrdemServicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoTarefaOrdemServicoFormService {
  createAnexoRequeridoTarefaOrdemServicoFormGroup(
    anexoRequeridoTarefaOrdemServico: AnexoRequeridoTarefaOrdemServicoFormGroupInput = { id: null },
  ): AnexoRequeridoTarefaOrdemServicoFormGroup {
    const anexoRequeridoTarefaOrdemServicoRawValue = {
      ...this.getFormDefaults(),
      ...anexoRequeridoTarefaOrdemServico,
    };
    return new FormGroup<AnexoRequeridoTarefaOrdemServicoFormGroupContent>({
      id: new FormControl(
        { value: anexoRequeridoTarefaOrdemServicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      obrigatorio: new FormControl(anexoRequeridoTarefaOrdemServicoRawValue.obrigatorio),
      anexoRequerido: new FormControl(anexoRequeridoTarefaOrdemServicoRawValue.anexoRequerido, {
        validators: [Validators.required],
      }),
      tarefaOrdemServico: new FormControl(anexoRequeridoTarefaOrdemServicoRawValue.tarefaOrdemServico, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoRequeridoTarefaOrdemServico(
    form: AnexoRequeridoTarefaOrdemServicoFormGroup,
  ): IAnexoRequeridoTarefaOrdemServico | NewAnexoRequeridoTarefaOrdemServico {
    return form.getRawValue() as IAnexoRequeridoTarefaOrdemServico | NewAnexoRequeridoTarefaOrdemServico;
  }

  resetForm(
    form: AnexoRequeridoTarefaOrdemServicoFormGroup,
    anexoRequeridoTarefaOrdemServico: AnexoRequeridoTarefaOrdemServicoFormGroupInput,
  ): void {
    const anexoRequeridoTarefaOrdemServicoRawValue = { ...this.getFormDefaults(), ...anexoRequeridoTarefaOrdemServico };
    form.reset(
      {
        ...anexoRequeridoTarefaOrdemServicoRawValue,
        id: { value: anexoRequeridoTarefaOrdemServicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoRequeridoTarefaOrdemServicoFormDefaults {
    return {
      id: null,
      obrigatorio: false,
    };
  }
}
