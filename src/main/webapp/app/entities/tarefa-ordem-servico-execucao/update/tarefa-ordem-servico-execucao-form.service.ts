import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITarefaOrdemServicoExecucao, NewTarefaOrdemServicoExecucao } from '../tarefa-ordem-servico-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarefaOrdemServicoExecucao for edit and NewTarefaOrdemServicoExecucaoFormGroupInput for create.
 */
type TarefaOrdemServicoExecucaoFormGroupInput = ITarefaOrdemServicoExecucao | PartialWithRequiredKeyOf<NewTarefaOrdemServicoExecucao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITarefaOrdemServicoExecucao | NewTarefaOrdemServicoExecucao> = Omit<T, 'dataEntrega' | 'dataAgendada'> & {
  dataEntrega?: string | null;
  dataAgendada?: string | null;
};

type TarefaOrdemServicoExecucaoFormRawValue = FormValueOf<ITarefaOrdemServicoExecucao>;

type NewTarefaOrdemServicoExecucaoFormRawValue = FormValueOf<NewTarefaOrdemServicoExecucao>;

type TarefaOrdemServicoExecucaoFormDefaults = Pick<
  NewTarefaOrdemServicoExecucao,
  'id' | 'dataEntrega' | 'dataAgendada' | 'concluida' | 'notificarCliente'
>;

type TarefaOrdemServicoExecucaoFormGroupContent = {
  id: FormControl<TarefaOrdemServicoExecucaoFormRawValue['id'] | NewTarefaOrdemServicoExecucao['id']>;
  nome: FormControl<TarefaOrdemServicoExecucaoFormRawValue['nome']>;
  descricao: FormControl<TarefaOrdemServicoExecucaoFormRawValue['descricao']>;
  ordem: FormControl<TarefaOrdemServicoExecucaoFormRawValue['ordem']>;
  dataEntrega: FormControl<TarefaOrdemServicoExecucaoFormRawValue['dataEntrega']>;
  dataAgendada: FormControl<TarefaOrdemServicoExecucaoFormRawValue['dataAgendada']>;
  concluida: FormControl<TarefaOrdemServicoExecucaoFormRawValue['concluida']>;
  notificarCliente: FormControl<TarefaOrdemServicoExecucaoFormRawValue['notificarCliente']>;
  mes: FormControl<TarefaOrdemServicoExecucaoFormRawValue['mes']>;
  situacaoTarefa: FormControl<TarefaOrdemServicoExecucaoFormRawValue['situacaoTarefa']>;
  tarefaOrdemServico: FormControl<TarefaOrdemServicoExecucaoFormRawValue['tarefaOrdemServico']>;
};

export type TarefaOrdemServicoExecucaoFormGroup = FormGroup<TarefaOrdemServicoExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TarefaOrdemServicoExecucaoFormService {
  createTarefaOrdemServicoExecucaoFormGroup(
    tarefaOrdemServicoExecucao: TarefaOrdemServicoExecucaoFormGroupInput = { id: null },
  ): TarefaOrdemServicoExecucaoFormGroup {
    const tarefaOrdemServicoExecucaoRawValue = this.convertTarefaOrdemServicoExecucaoToTarefaOrdemServicoExecucaoRawValue({
      ...this.getFormDefaults(),
      ...tarefaOrdemServicoExecucao,
    });
    return new FormGroup<TarefaOrdemServicoExecucaoFormGroupContent>({
      id: new FormControl(
        { value: tarefaOrdemServicoExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(tarefaOrdemServicoExecucaoRawValue.nome),
      descricao: new FormControl(tarefaOrdemServicoExecucaoRawValue.descricao),
      ordem: new FormControl(tarefaOrdemServicoExecucaoRawValue.ordem),
      dataEntrega: new FormControl(tarefaOrdemServicoExecucaoRawValue.dataEntrega),
      dataAgendada: new FormControl(tarefaOrdemServicoExecucaoRawValue.dataAgendada),
      concluida: new FormControl(tarefaOrdemServicoExecucaoRawValue.concluida),
      notificarCliente: new FormControl(tarefaOrdemServicoExecucaoRawValue.notificarCliente),
      mes: new FormControl(tarefaOrdemServicoExecucaoRawValue.mes),
      situacaoTarefa: new FormControl(tarefaOrdemServicoExecucaoRawValue.situacaoTarefa),
      tarefaOrdemServico: new FormControl(tarefaOrdemServicoExecucaoRawValue.tarefaOrdemServico, {
        validators: [Validators.required],
      }),
    });
  }

  getTarefaOrdemServicoExecucao(form: TarefaOrdemServicoExecucaoFormGroup): ITarefaOrdemServicoExecucao | NewTarefaOrdemServicoExecucao {
    return this.convertTarefaOrdemServicoExecucaoRawValueToTarefaOrdemServicoExecucao(
      form.getRawValue() as TarefaOrdemServicoExecucaoFormRawValue | NewTarefaOrdemServicoExecucaoFormRawValue,
    );
  }

  resetForm(form: TarefaOrdemServicoExecucaoFormGroup, tarefaOrdemServicoExecucao: TarefaOrdemServicoExecucaoFormGroupInput): void {
    const tarefaOrdemServicoExecucaoRawValue = this.convertTarefaOrdemServicoExecucaoToTarefaOrdemServicoExecucaoRawValue({
      ...this.getFormDefaults(),
      ...tarefaOrdemServicoExecucao,
    });
    form.reset(
      {
        ...tarefaOrdemServicoExecucaoRawValue,
        id: { value: tarefaOrdemServicoExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TarefaOrdemServicoExecucaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataEntrega: currentTime,
      dataAgendada: currentTime,
      concluida: false,
      notificarCliente: false,
    };
  }

  private convertTarefaOrdemServicoExecucaoRawValueToTarefaOrdemServicoExecucao(
    rawTarefaOrdemServicoExecucao: TarefaOrdemServicoExecucaoFormRawValue | NewTarefaOrdemServicoExecucaoFormRawValue,
  ): ITarefaOrdemServicoExecucao | NewTarefaOrdemServicoExecucao {
    return {
      ...rawTarefaOrdemServicoExecucao,
      dataEntrega: dayjs(rawTarefaOrdemServicoExecucao.dataEntrega, DATE_TIME_FORMAT),
      dataAgendada: dayjs(rawTarefaOrdemServicoExecucao.dataAgendada, DATE_TIME_FORMAT),
    };
  }

  private convertTarefaOrdemServicoExecucaoToTarefaOrdemServicoExecucaoRawValue(
    tarefaOrdemServicoExecucao:
      | ITarefaOrdemServicoExecucao
      | (Partial<NewTarefaOrdemServicoExecucao> & TarefaOrdemServicoExecucaoFormDefaults),
  ): TarefaOrdemServicoExecucaoFormRawValue | PartialWithRequiredKeyOf<NewTarefaOrdemServicoExecucaoFormRawValue> {
    return {
      ...tarefaOrdemServicoExecucao,
      dataEntrega: tarefaOrdemServicoExecucao.dataEntrega ? tarefaOrdemServicoExecucao.dataEntrega.format(DATE_TIME_FORMAT) : undefined,
      dataAgendada: tarefaOrdemServicoExecucao.dataAgendada ? tarefaOrdemServicoExecucao.dataAgendada.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
