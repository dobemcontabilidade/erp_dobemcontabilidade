import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAgendaTarefaOrdemServicoExecucao, NewAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgendaTarefaOrdemServicoExecucao for edit and NewAgendaTarefaOrdemServicoExecucaoFormGroupInput for create.
 */
type AgendaTarefaOrdemServicoExecucaoFormGroupInput =
  | IAgendaTarefaOrdemServicoExecucao
  | PartialWithRequiredKeyOf<NewAgendaTarefaOrdemServicoExecucao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAgendaTarefaOrdemServicoExecucao | NewAgendaTarefaOrdemServicoExecucao> = Omit<T, 'horaInicio' | 'horaFim'> & {
  horaInicio?: string | null;
  horaFim?: string | null;
};

type AgendaTarefaOrdemServicoExecucaoFormRawValue = FormValueOf<IAgendaTarefaOrdemServicoExecucao>;

type NewAgendaTarefaOrdemServicoExecucaoFormRawValue = FormValueOf<NewAgendaTarefaOrdemServicoExecucao>;

type AgendaTarefaOrdemServicoExecucaoFormDefaults = Pick<
  NewAgendaTarefaOrdemServicoExecucao,
  'id' | 'horaInicio' | 'horaFim' | 'diaInteiro' | 'ativo'
>;

type AgendaTarefaOrdemServicoExecucaoFormGroupContent = {
  id: FormControl<AgendaTarefaOrdemServicoExecucaoFormRawValue['id'] | NewAgendaTarefaOrdemServicoExecucao['id']>;
  horaInicio: FormControl<AgendaTarefaOrdemServicoExecucaoFormRawValue['horaInicio']>;
  horaFim: FormControl<AgendaTarefaOrdemServicoExecucaoFormRawValue['horaFim']>;
  diaInteiro: FormControl<AgendaTarefaOrdemServicoExecucaoFormRawValue['diaInteiro']>;
  ativo: FormControl<AgendaTarefaOrdemServicoExecucaoFormRawValue['ativo']>;
  tarefaOrdemServicoExecucao: FormControl<AgendaTarefaOrdemServicoExecucaoFormRawValue['tarefaOrdemServicoExecucao']>;
};

export type AgendaTarefaOrdemServicoExecucaoFormGroup = FormGroup<AgendaTarefaOrdemServicoExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgendaTarefaOrdemServicoExecucaoFormService {
  createAgendaTarefaOrdemServicoExecucaoFormGroup(
    agendaTarefaOrdemServicoExecucao: AgendaTarefaOrdemServicoExecucaoFormGroupInput = { id: null },
  ): AgendaTarefaOrdemServicoExecucaoFormGroup {
    const agendaTarefaOrdemServicoExecucaoRawValue = this.convertAgendaTarefaOrdemServicoExecucaoToAgendaTarefaOrdemServicoExecucaoRawValue(
      {
        ...this.getFormDefaults(),
        ...agendaTarefaOrdemServicoExecucao,
      },
    );
    return new FormGroup<AgendaTarefaOrdemServicoExecucaoFormGroupContent>({
      id: new FormControl(
        { value: agendaTarefaOrdemServicoExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      horaInicio: new FormControl(agendaTarefaOrdemServicoExecucaoRawValue.horaInicio),
      horaFim: new FormControl(agendaTarefaOrdemServicoExecucaoRawValue.horaFim),
      diaInteiro: new FormControl(agendaTarefaOrdemServicoExecucaoRawValue.diaInteiro),
      ativo: new FormControl(agendaTarefaOrdemServicoExecucaoRawValue.ativo),
      tarefaOrdemServicoExecucao: new FormControl(agendaTarefaOrdemServicoExecucaoRawValue.tarefaOrdemServicoExecucao, {
        validators: [Validators.required],
      }),
    });
  }

  getAgendaTarefaOrdemServicoExecucao(
    form: AgendaTarefaOrdemServicoExecucaoFormGroup,
  ): IAgendaTarefaOrdemServicoExecucao | NewAgendaTarefaOrdemServicoExecucao {
    return this.convertAgendaTarefaOrdemServicoExecucaoRawValueToAgendaTarefaOrdemServicoExecucao(
      form.getRawValue() as AgendaTarefaOrdemServicoExecucaoFormRawValue | NewAgendaTarefaOrdemServicoExecucaoFormRawValue,
    );
  }

  resetForm(
    form: AgendaTarefaOrdemServicoExecucaoFormGroup,
    agendaTarefaOrdemServicoExecucao: AgendaTarefaOrdemServicoExecucaoFormGroupInput,
  ): void {
    const agendaTarefaOrdemServicoExecucaoRawValue = this.convertAgendaTarefaOrdemServicoExecucaoToAgendaTarefaOrdemServicoExecucaoRawValue(
      { ...this.getFormDefaults(), ...agendaTarefaOrdemServicoExecucao },
    );
    form.reset(
      {
        ...agendaTarefaOrdemServicoExecucaoRawValue,
        id: { value: agendaTarefaOrdemServicoExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgendaTarefaOrdemServicoExecucaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      horaInicio: currentTime,
      horaFim: currentTime,
      diaInteiro: false,
      ativo: false,
    };
  }

  private convertAgendaTarefaOrdemServicoExecucaoRawValueToAgendaTarefaOrdemServicoExecucao(
    rawAgendaTarefaOrdemServicoExecucao: AgendaTarefaOrdemServicoExecucaoFormRawValue | NewAgendaTarefaOrdemServicoExecucaoFormRawValue,
  ): IAgendaTarefaOrdemServicoExecucao | NewAgendaTarefaOrdemServicoExecucao {
    return {
      ...rawAgendaTarefaOrdemServicoExecucao,
      horaInicio: dayjs(rawAgendaTarefaOrdemServicoExecucao.horaInicio, DATE_TIME_FORMAT),
      horaFim: dayjs(rawAgendaTarefaOrdemServicoExecucao.horaFim, DATE_TIME_FORMAT),
    };
  }

  private convertAgendaTarefaOrdemServicoExecucaoToAgendaTarefaOrdemServicoExecucaoRawValue(
    agendaTarefaOrdemServicoExecucao:
      | IAgendaTarefaOrdemServicoExecucao
      | (Partial<NewAgendaTarefaOrdemServicoExecucao> & AgendaTarefaOrdemServicoExecucaoFormDefaults),
  ): AgendaTarefaOrdemServicoExecucaoFormRawValue | PartialWithRequiredKeyOf<NewAgendaTarefaOrdemServicoExecucaoFormRawValue> {
    return {
      ...agendaTarefaOrdemServicoExecucao,
      horaInicio: agendaTarefaOrdemServicoExecucao.horaInicio
        ? agendaTarefaOrdemServicoExecucao.horaInicio.format(DATE_TIME_FORMAT)
        : undefined,
      horaFim: agendaTarefaOrdemServicoExecucao.horaFim ? agendaTarefaOrdemServicoExecucao.horaFim.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
