import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITarefaOrdemServico, NewTarefaOrdemServico } from '../tarefa-ordem-servico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarefaOrdemServico for edit and NewTarefaOrdemServicoFormGroupInput for create.
 */
type TarefaOrdemServicoFormGroupInput = ITarefaOrdemServico | PartialWithRequiredKeyOf<NewTarefaOrdemServico>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITarefaOrdemServico | NewTarefaOrdemServico> = Omit<T, 'dataAdmin'> & {
  dataAdmin?: string | null;
};

type TarefaOrdemServicoFormRawValue = FormValueOf<ITarefaOrdemServico>;

type NewTarefaOrdemServicoFormRawValue = FormValueOf<NewTarefaOrdemServico>;

type TarefaOrdemServicoFormDefaults = Pick<NewTarefaOrdemServico, 'id' | 'notificarCliente' | 'notificarContador' | 'dataAdmin'>;

type TarefaOrdemServicoFormGroupContent = {
  id: FormControl<TarefaOrdemServicoFormRawValue['id'] | NewTarefaOrdemServico['id']>;
  nome: FormControl<TarefaOrdemServicoFormRawValue['nome']>;
  descricao: FormControl<TarefaOrdemServicoFormRawValue['descricao']>;
  notificarCliente: FormControl<TarefaOrdemServicoFormRawValue['notificarCliente']>;
  notificarContador: FormControl<TarefaOrdemServicoFormRawValue['notificarContador']>;
  anoReferencia: FormControl<TarefaOrdemServicoFormRawValue['anoReferencia']>;
  dataAdmin: FormControl<TarefaOrdemServicoFormRawValue['dataAdmin']>;
  servicoContabilOrdemServico: FormControl<TarefaOrdemServicoFormRawValue['servicoContabilOrdemServico']>;
};

export type TarefaOrdemServicoFormGroup = FormGroup<TarefaOrdemServicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TarefaOrdemServicoFormService {
  createTarefaOrdemServicoFormGroup(tarefaOrdemServico: TarefaOrdemServicoFormGroupInput = { id: null }): TarefaOrdemServicoFormGroup {
    const tarefaOrdemServicoRawValue = this.convertTarefaOrdemServicoToTarefaOrdemServicoRawValue({
      ...this.getFormDefaults(),
      ...tarefaOrdemServico,
    });
    return new FormGroup<TarefaOrdemServicoFormGroupContent>({
      id: new FormControl(
        { value: tarefaOrdemServicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(tarefaOrdemServicoRawValue.nome),
      descricao: new FormControl(tarefaOrdemServicoRawValue.descricao),
      notificarCliente: new FormControl(tarefaOrdemServicoRawValue.notificarCliente),
      notificarContador: new FormControl(tarefaOrdemServicoRawValue.notificarContador),
      anoReferencia: new FormControl(tarefaOrdemServicoRawValue.anoReferencia),
      dataAdmin: new FormControl(tarefaOrdemServicoRawValue.dataAdmin),
      servicoContabilOrdemServico: new FormControl(tarefaOrdemServicoRawValue.servicoContabilOrdemServico, {
        validators: [Validators.required],
      }),
    });
  }

  getTarefaOrdemServico(form: TarefaOrdemServicoFormGroup): ITarefaOrdemServico | NewTarefaOrdemServico {
    return this.convertTarefaOrdemServicoRawValueToTarefaOrdemServico(
      form.getRawValue() as TarefaOrdemServicoFormRawValue | NewTarefaOrdemServicoFormRawValue,
    );
  }

  resetForm(form: TarefaOrdemServicoFormGroup, tarefaOrdemServico: TarefaOrdemServicoFormGroupInput): void {
    const tarefaOrdemServicoRawValue = this.convertTarefaOrdemServicoToTarefaOrdemServicoRawValue({
      ...this.getFormDefaults(),
      ...tarefaOrdemServico,
    });
    form.reset(
      {
        ...tarefaOrdemServicoRawValue,
        id: { value: tarefaOrdemServicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TarefaOrdemServicoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      notificarCliente: false,
      notificarContador: false,
      dataAdmin: currentTime,
    };
  }

  private convertTarefaOrdemServicoRawValueToTarefaOrdemServico(
    rawTarefaOrdemServico: TarefaOrdemServicoFormRawValue | NewTarefaOrdemServicoFormRawValue,
  ): ITarefaOrdemServico | NewTarefaOrdemServico {
    return {
      ...rawTarefaOrdemServico,
      dataAdmin: dayjs(rawTarefaOrdemServico.dataAdmin, DATE_TIME_FORMAT),
    };
  }

  private convertTarefaOrdemServicoToTarefaOrdemServicoRawValue(
    tarefaOrdemServico: ITarefaOrdemServico | (Partial<NewTarefaOrdemServico> & TarefaOrdemServicoFormDefaults),
  ): TarefaOrdemServicoFormRawValue | PartialWithRequiredKeyOf<NewTarefaOrdemServicoFormRawValue> {
    return {
      ...tarefaOrdemServico,
      dataAdmin: tarefaOrdemServico.dataAdmin ? tarefaOrdemServico.dataAdmin.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
