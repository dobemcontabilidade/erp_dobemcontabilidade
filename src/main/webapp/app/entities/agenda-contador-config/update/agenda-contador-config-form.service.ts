import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAgendaContadorConfig, NewAgendaContadorConfig } from '../agenda-contador-config.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgendaContadorConfig for edit and NewAgendaContadorConfigFormGroupInput for create.
 */
type AgendaContadorConfigFormGroupInput = IAgendaContadorConfig | PartialWithRequiredKeyOf<NewAgendaContadorConfig>;

type AgendaContadorConfigFormDefaults = Pick<NewAgendaContadorConfig, 'id' | 'ativo'>;

type AgendaContadorConfigFormGroupContent = {
  id: FormControl<IAgendaContadorConfig['id'] | NewAgendaContadorConfig['id']>;
  ativo: FormControl<IAgendaContadorConfig['ativo']>;
  tipoVisualizacaoAgendaEnum: FormControl<IAgendaContadorConfig['tipoVisualizacaoAgendaEnum']>;
  agendaTarefaRecorrenteExecucao: FormControl<IAgendaContadorConfig['agendaTarefaRecorrenteExecucao']>;
  agendaTarefaOrdemServicoExecucao: FormControl<IAgendaContadorConfig['agendaTarefaOrdemServicoExecucao']>;
};

export type AgendaContadorConfigFormGroup = FormGroup<AgendaContadorConfigFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgendaContadorConfigFormService {
  createAgendaContadorConfigFormGroup(
    agendaContadorConfig: AgendaContadorConfigFormGroupInput = { id: null },
  ): AgendaContadorConfigFormGroup {
    const agendaContadorConfigRawValue = {
      ...this.getFormDefaults(),
      ...agendaContadorConfig,
    };
    return new FormGroup<AgendaContadorConfigFormGroupContent>({
      id: new FormControl(
        { value: agendaContadorConfigRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ativo: new FormControl(agendaContadorConfigRawValue.ativo),
      tipoVisualizacaoAgendaEnum: new FormControl(agendaContadorConfigRawValue.tipoVisualizacaoAgendaEnum),
      agendaTarefaRecorrenteExecucao: new FormControl(agendaContadorConfigRawValue.agendaTarefaRecorrenteExecucao, {
        validators: [Validators.required],
      }),
      agendaTarefaOrdemServicoExecucao: new FormControl(agendaContadorConfigRawValue.agendaTarefaOrdemServicoExecucao, {
        validators: [Validators.required],
      }),
    });
  }

  getAgendaContadorConfig(form: AgendaContadorConfigFormGroup): IAgendaContadorConfig | NewAgendaContadorConfig {
    return form.getRawValue() as IAgendaContadorConfig | NewAgendaContadorConfig;
  }

  resetForm(form: AgendaContadorConfigFormGroup, agendaContadorConfig: AgendaContadorConfigFormGroupInput): void {
    const agendaContadorConfigRawValue = { ...this.getFormDefaults(), ...agendaContadorConfig };
    form.reset(
      {
        ...agendaContadorConfigRawValue,
        id: { value: agendaContadorConfigRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgendaContadorConfigFormDefaults {
    return {
      id: null,
      ativo: false,
    };
  }
}
