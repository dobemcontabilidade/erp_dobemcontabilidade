import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITarefaRecorrente, NewTarefaRecorrente } from '../tarefa-recorrente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarefaRecorrente for edit and NewTarefaRecorrenteFormGroupInput for create.
 */
type TarefaRecorrenteFormGroupInput = ITarefaRecorrente | PartialWithRequiredKeyOf<NewTarefaRecorrente>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITarefaRecorrente | NewTarefaRecorrente> = Omit<T, 'dataAdmin'> & {
  dataAdmin?: string | null;
};

type TarefaRecorrenteFormRawValue = FormValueOf<ITarefaRecorrente>;

type NewTarefaRecorrenteFormRawValue = FormValueOf<NewTarefaRecorrente>;

type TarefaRecorrenteFormDefaults = Pick<NewTarefaRecorrente, 'id' | 'notificarCliente' | 'notificarContador' | 'dataAdmin'>;

type TarefaRecorrenteFormGroupContent = {
  id: FormControl<TarefaRecorrenteFormRawValue['id'] | NewTarefaRecorrente['id']>;
  nome: FormControl<TarefaRecorrenteFormRawValue['nome']>;
  descricao: FormControl<TarefaRecorrenteFormRawValue['descricao']>;
  notificarCliente: FormControl<TarefaRecorrenteFormRawValue['notificarCliente']>;
  notificarContador: FormControl<TarefaRecorrenteFormRawValue['notificarContador']>;
  anoReferencia: FormControl<TarefaRecorrenteFormRawValue['anoReferencia']>;
  dataAdmin: FormControl<TarefaRecorrenteFormRawValue['dataAdmin']>;
  recorencia: FormControl<TarefaRecorrenteFormRawValue['recorencia']>;
  servicoContabilAssinaturaEmpresa: FormControl<TarefaRecorrenteFormRawValue['servicoContabilAssinaturaEmpresa']>;
};

export type TarefaRecorrenteFormGroup = FormGroup<TarefaRecorrenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TarefaRecorrenteFormService {
  createTarefaRecorrenteFormGroup(tarefaRecorrente: TarefaRecorrenteFormGroupInput = { id: null }): TarefaRecorrenteFormGroup {
    const tarefaRecorrenteRawValue = this.convertTarefaRecorrenteToTarefaRecorrenteRawValue({
      ...this.getFormDefaults(),
      ...tarefaRecorrente,
    });
    return new FormGroup<TarefaRecorrenteFormGroupContent>({
      id: new FormControl(
        { value: tarefaRecorrenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(tarefaRecorrenteRawValue.nome),
      descricao: new FormControl(tarefaRecorrenteRawValue.descricao),
      notificarCliente: new FormControl(tarefaRecorrenteRawValue.notificarCliente),
      notificarContador: new FormControl(tarefaRecorrenteRawValue.notificarContador),
      anoReferencia: new FormControl(tarefaRecorrenteRawValue.anoReferencia),
      dataAdmin: new FormControl(tarefaRecorrenteRawValue.dataAdmin),
      recorencia: new FormControl(tarefaRecorrenteRawValue.recorencia),
      servicoContabilAssinaturaEmpresa: new FormControl(tarefaRecorrenteRawValue.servicoContabilAssinaturaEmpresa, {
        validators: [Validators.required],
      }),
    });
  }

  getTarefaRecorrente(form: TarefaRecorrenteFormGroup): ITarefaRecorrente | NewTarefaRecorrente {
    return this.convertTarefaRecorrenteRawValueToTarefaRecorrente(
      form.getRawValue() as TarefaRecorrenteFormRawValue | NewTarefaRecorrenteFormRawValue,
    );
  }

  resetForm(form: TarefaRecorrenteFormGroup, tarefaRecorrente: TarefaRecorrenteFormGroupInput): void {
    const tarefaRecorrenteRawValue = this.convertTarefaRecorrenteToTarefaRecorrenteRawValue({
      ...this.getFormDefaults(),
      ...tarefaRecorrente,
    });
    form.reset(
      {
        ...tarefaRecorrenteRawValue,
        id: { value: tarefaRecorrenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TarefaRecorrenteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      notificarCliente: false,
      notificarContador: false,
      dataAdmin: currentTime,
    };
  }

  private convertTarefaRecorrenteRawValueToTarefaRecorrente(
    rawTarefaRecorrente: TarefaRecorrenteFormRawValue | NewTarefaRecorrenteFormRawValue,
  ): ITarefaRecorrente | NewTarefaRecorrente {
    return {
      ...rawTarefaRecorrente,
      dataAdmin: dayjs(rawTarefaRecorrente.dataAdmin, DATE_TIME_FORMAT),
    };
  }

  private convertTarefaRecorrenteToTarefaRecorrenteRawValue(
    tarefaRecorrente: ITarefaRecorrente | (Partial<NewTarefaRecorrente> & TarefaRecorrenteFormDefaults),
  ): TarefaRecorrenteFormRawValue | PartialWithRequiredKeyOf<NewTarefaRecorrenteFormRawValue> {
    return {
      ...tarefaRecorrente,
      dataAdmin: tarefaRecorrente.dataAdmin ? tarefaRecorrente.dataAdmin.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
