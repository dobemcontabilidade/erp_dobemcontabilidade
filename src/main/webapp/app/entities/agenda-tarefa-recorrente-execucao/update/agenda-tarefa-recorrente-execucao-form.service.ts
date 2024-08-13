import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAgendaTarefaRecorrenteExecucao, NewAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgendaTarefaRecorrenteExecucao for edit and NewAgendaTarefaRecorrenteExecucaoFormGroupInput for create.
 */
type AgendaTarefaRecorrenteExecucaoFormGroupInput =
  | IAgendaTarefaRecorrenteExecucao
  | PartialWithRequiredKeyOf<NewAgendaTarefaRecorrenteExecucao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAgendaTarefaRecorrenteExecucao | NewAgendaTarefaRecorrenteExecucao> = Omit<T, 'horaInicio' | 'horaFim'> & {
  horaInicio?: string | null;
  horaFim?: string | null;
};

type AgendaTarefaRecorrenteExecucaoFormRawValue = FormValueOf<IAgendaTarefaRecorrenteExecucao>;

type NewAgendaTarefaRecorrenteExecucaoFormRawValue = FormValueOf<NewAgendaTarefaRecorrenteExecucao>;

type AgendaTarefaRecorrenteExecucaoFormDefaults = Pick<
  NewAgendaTarefaRecorrenteExecucao,
  'id' | 'ativo' | 'horaInicio' | 'horaFim' | 'diaInteiro'
>;

type AgendaTarefaRecorrenteExecucaoFormGroupContent = {
  id: FormControl<AgendaTarefaRecorrenteExecucaoFormRawValue['id'] | NewAgendaTarefaRecorrenteExecucao['id']>;
  ativo: FormControl<AgendaTarefaRecorrenteExecucaoFormRawValue['ativo']>;
  horaInicio: FormControl<AgendaTarefaRecorrenteExecucaoFormRawValue['horaInicio']>;
  horaFim: FormControl<AgendaTarefaRecorrenteExecucaoFormRawValue['horaFim']>;
  diaInteiro: FormControl<AgendaTarefaRecorrenteExecucaoFormRawValue['diaInteiro']>;
  comentario: FormControl<AgendaTarefaRecorrenteExecucaoFormRawValue['comentario']>;
  tarefaRecorrenteExecucao: FormControl<AgendaTarefaRecorrenteExecucaoFormRawValue['tarefaRecorrenteExecucao']>;
};

export type AgendaTarefaRecorrenteExecucaoFormGroup = FormGroup<AgendaTarefaRecorrenteExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgendaTarefaRecorrenteExecucaoFormService {
  createAgendaTarefaRecorrenteExecucaoFormGroup(
    agendaTarefaRecorrenteExecucao: AgendaTarefaRecorrenteExecucaoFormGroupInput = { id: null },
  ): AgendaTarefaRecorrenteExecucaoFormGroup {
    const agendaTarefaRecorrenteExecucaoRawValue = this.convertAgendaTarefaRecorrenteExecucaoToAgendaTarefaRecorrenteExecucaoRawValue({
      ...this.getFormDefaults(),
      ...agendaTarefaRecorrenteExecucao,
    });
    return new FormGroup<AgendaTarefaRecorrenteExecucaoFormGroupContent>({
      id: new FormControl(
        { value: agendaTarefaRecorrenteExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ativo: new FormControl(agendaTarefaRecorrenteExecucaoRawValue.ativo),
      horaInicio: new FormControl(agendaTarefaRecorrenteExecucaoRawValue.horaInicio),
      horaFim: new FormControl(agendaTarefaRecorrenteExecucaoRawValue.horaFim),
      diaInteiro: new FormControl(agendaTarefaRecorrenteExecucaoRawValue.diaInteiro),
      comentario: new FormControl(agendaTarefaRecorrenteExecucaoRawValue.comentario),
      tarefaRecorrenteExecucao: new FormControl(agendaTarefaRecorrenteExecucaoRawValue.tarefaRecorrenteExecucao, {
        validators: [Validators.required],
      }),
    });
  }

  getAgendaTarefaRecorrenteExecucao(
    form: AgendaTarefaRecorrenteExecucaoFormGroup,
  ): IAgendaTarefaRecorrenteExecucao | NewAgendaTarefaRecorrenteExecucao {
    return this.convertAgendaTarefaRecorrenteExecucaoRawValueToAgendaTarefaRecorrenteExecucao(
      form.getRawValue() as AgendaTarefaRecorrenteExecucaoFormRawValue | NewAgendaTarefaRecorrenteExecucaoFormRawValue,
    );
  }

  resetForm(
    form: AgendaTarefaRecorrenteExecucaoFormGroup,
    agendaTarefaRecorrenteExecucao: AgendaTarefaRecorrenteExecucaoFormGroupInput,
  ): void {
    const agendaTarefaRecorrenteExecucaoRawValue = this.convertAgendaTarefaRecorrenteExecucaoToAgendaTarefaRecorrenteExecucaoRawValue({
      ...this.getFormDefaults(),
      ...agendaTarefaRecorrenteExecucao,
    });
    form.reset(
      {
        ...agendaTarefaRecorrenteExecucaoRawValue,
        id: { value: agendaTarefaRecorrenteExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgendaTarefaRecorrenteExecucaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ativo: false,
      horaInicio: currentTime,
      horaFim: currentTime,
      diaInteiro: false,
    };
  }

  private convertAgendaTarefaRecorrenteExecucaoRawValueToAgendaTarefaRecorrenteExecucao(
    rawAgendaTarefaRecorrenteExecucao: AgendaTarefaRecorrenteExecucaoFormRawValue | NewAgendaTarefaRecorrenteExecucaoFormRawValue,
  ): IAgendaTarefaRecorrenteExecucao | NewAgendaTarefaRecorrenteExecucao {
    return {
      ...rawAgendaTarefaRecorrenteExecucao,
      horaInicio: dayjs(rawAgendaTarefaRecorrenteExecucao.horaInicio, DATE_TIME_FORMAT),
      horaFim: dayjs(rawAgendaTarefaRecorrenteExecucao.horaFim, DATE_TIME_FORMAT),
    };
  }

  private convertAgendaTarefaRecorrenteExecucaoToAgendaTarefaRecorrenteExecucaoRawValue(
    agendaTarefaRecorrenteExecucao:
      | IAgendaTarefaRecorrenteExecucao
      | (Partial<NewAgendaTarefaRecorrenteExecucao> & AgendaTarefaRecorrenteExecucaoFormDefaults),
  ): AgendaTarefaRecorrenteExecucaoFormRawValue | PartialWithRequiredKeyOf<NewAgendaTarefaRecorrenteExecucaoFormRawValue> {
    return {
      ...agendaTarefaRecorrenteExecucao,
      horaInicio: agendaTarefaRecorrenteExecucao.horaInicio
        ? agendaTarefaRecorrenteExecucao.horaInicio.format(DATE_TIME_FORMAT)
        : undefined,
      horaFim: agendaTarefaRecorrenteExecucao.horaFim ? agendaTarefaRecorrenteExecucao.horaFim.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
