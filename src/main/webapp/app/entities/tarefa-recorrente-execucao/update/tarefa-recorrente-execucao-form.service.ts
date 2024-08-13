import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITarefaRecorrenteExecucao, NewTarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarefaRecorrenteExecucao for edit and NewTarefaRecorrenteExecucaoFormGroupInput for create.
 */
type TarefaRecorrenteExecucaoFormGroupInput = ITarefaRecorrenteExecucao | PartialWithRequiredKeyOf<NewTarefaRecorrenteExecucao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITarefaRecorrenteExecucao | NewTarefaRecorrenteExecucao> = Omit<T, 'dataEntrega' | 'dataAgendada'> & {
  dataEntrega?: string | null;
  dataAgendada?: string | null;
};

type TarefaRecorrenteExecucaoFormRawValue = FormValueOf<ITarefaRecorrenteExecucao>;

type NewTarefaRecorrenteExecucaoFormRawValue = FormValueOf<NewTarefaRecorrenteExecucao>;

type TarefaRecorrenteExecucaoFormDefaults = Pick<NewTarefaRecorrenteExecucao, 'id' | 'dataEntrega' | 'dataAgendada' | 'concluida'>;

type TarefaRecorrenteExecucaoFormGroupContent = {
  id: FormControl<TarefaRecorrenteExecucaoFormRawValue['id'] | NewTarefaRecorrenteExecucao['id']>;
  nome: FormControl<TarefaRecorrenteExecucaoFormRawValue['nome']>;
  descricao: FormControl<TarefaRecorrenteExecucaoFormRawValue['descricao']>;
  dataEntrega: FormControl<TarefaRecorrenteExecucaoFormRawValue['dataEntrega']>;
  dataAgendada: FormControl<TarefaRecorrenteExecucaoFormRawValue['dataAgendada']>;
  ordem: FormControl<TarefaRecorrenteExecucaoFormRawValue['ordem']>;
  concluida: FormControl<TarefaRecorrenteExecucaoFormRawValue['concluida']>;
  mes: FormControl<TarefaRecorrenteExecucaoFormRawValue['mes']>;
  situacaoTarefa: FormControl<TarefaRecorrenteExecucaoFormRawValue['situacaoTarefa']>;
  tarefaRecorrente: FormControl<TarefaRecorrenteExecucaoFormRawValue['tarefaRecorrente']>;
};

export type TarefaRecorrenteExecucaoFormGroup = FormGroup<TarefaRecorrenteExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TarefaRecorrenteExecucaoFormService {
  createTarefaRecorrenteExecucaoFormGroup(
    tarefaRecorrenteExecucao: TarefaRecorrenteExecucaoFormGroupInput = { id: null },
  ): TarefaRecorrenteExecucaoFormGroup {
    const tarefaRecorrenteExecucaoRawValue = this.convertTarefaRecorrenteExecucaoToTarefaRecorrenteExecucaoRawValue({
      ...this.getFormDefaults(),
      ...tarefaRecorrenteExecucao,
    });
    return new FormGroup<TarefaRecorrenteExecucaoFormGroupContent>({
      id: new FormControl(
        { value: tarefaRecorrenteExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(tarefaRecorrenteExecucaoRawValue.nome),
      descricao: new FormControl(tarefaRecorrenteExecucaoRawValue.descricao),
      dataEntrega: new FormControl(tarefaRecorrenteExecucaoRawValue.dataEntrega),
      dataAgendada: new FormControl(tarefaRecorrenteExecucaoRawValue.dataAgendada),
      ordem: new FormControl(tarefaRecorrenteExecucaoRawValue.ordem),
      concluida: new FormControl(tarefaRecorrenteExecucaoRawValue.concluida),
      mes: new FormControl(tarefaRecorrenteExecucaoRawValue.mes),
      situacaoTarefa: new FormControl(tarefaRecorrenteExecucaoRawValue.situacaoTarefa),
      tarefaRecorrente: new FormControl(tarefaRecorrenteExecucaoRawValue.tarefaRecorrente, {
        validators: [Validators.required],
      }),
    });
  }

  getTarefaRecorrenteExecucao(form: TarefaRecorrenteExecucaoFormGroup): ITarefaRecorrenteExecucao | NewTarefaRecorrenteExecucao {
    return this.convertTarefaRecorrenteExecucaoRawValueToTarefaRecorrenteExecucao(
      form.getRawValue() as TarefaRecorrenteExecucaoFormRawValue | NewTarefaRecorrenteExecucaoFormRawValue,
    );
  }

  resetForm(form: TarefaRecorrenteExecucaoFormGroup, tarefaRecorrenteExecucao: TarefaRecorrenteExecucaoFormGroupInput): void {
    const tarefaRecorrenteExecucaoRawValue = this.convertTarefaRecorrenteExecucaoToTarefaRecorrenteExecucaoRawValue({
      ...this.getFormDefaults(),
      ...tarefaRecorrenteExecucao,
    });
    form.reset(
      {
        ...tarefaRecorrenteExecucaoRawValue,
        id: { value: tarefaRecorrenteExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TarefaRecorrenteExecucaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataEntrega: currentTime,
      dataAgendada: currentTime,
      concluida: false,
    };
  }

  private convertTarefaRecorrenteExecucaoRawValueToTarefaRecorrenteExecucao(
    rawTarefaRecorrenteExecucao: TarefaRecorrenteExecucaoFormRawValue | NewTarefaRecorrenteExecucaoFormRawValue,
  ): ITarefaRecorrenteExecucao | NewTarefaRecorrenteExecucao {
    return {
      ...rawTarefaRecorrenteExecucao,
      dataEntrega: dayjs(rawTarefaRecorrenteExecucao.dataEntrega, DATE_TIME_FORMAT),
      dataAgendada: dayjs(rawTarefaRecorrenteExecucao.dataAgendada, DATE_TIME_FORMAT),
    };
  }

  private convertTarefaRecorrenteExecucaoToTarefaRecorrenteExecucaoRawValue(
    tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao | (Partial<NewTarefaRecorrenteExecucao> & TarefaRecorrenteExecucaoFormDefaults),
  ): TarefaRecorrenteExecucaoFormRawValue | PartialWithRequiredKeyOf<NewTarefaRecorrenteExecucaoFormRawValue> {
    return {
      ...tarefaRecorrenteExecucao,
      dataEntrega: tarefaRecorrenteExecucao.dataEntrega ? tarefaRecorrenteExecucao.dataEntrega.format(DATE_TIME_FORMAT) : undefined,
      dataAgendada: tarefaRecorrenteExecucao.dataAgendada ? tarefaRecorrenteExecucao.dataAgendada.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
