import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import {
  IContadorResponsavelTarefaRecorrente,
  NewContadorResponsavelTarefaRecorrente,
} from '../contador-responsavel-tarefa-recorrente.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContadorResponsavelTarefaRecorrente for edit and NewContadorResponsavelTarefaRecorrenteFormGroupInput for create.
 */
type ContadorResponsavelTarefaRecorrenteFormGroupInput =
  | IContadorResponsavelTarefaRecorrente
  | PartialWithRequiredKeyOf<NewContadorResponsavelTarefaRecorrente>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IContadorResponsavelTarefaRecorrente | NewContadorResponsavelTarefaRecorrente> = Omit<
  T,
  'dataAtribuicao' | 'dataRevogacao'
> & {
  dataAtribuicao?: string | null;
  dataRevogacao?: string | null;
};

type ContadorResponsavelTarefaRecorrenteFormRawValue = FormValueOf<IContadorResponsavelTarefaRecorrente>;

type NewContadorResponsavelTarefaRecorrenteFormRawValue = FormValueOf<NewContadorResponsavelTarefaRecorrente>;

type ContadorResponsavelTarefaRecorrenteFormDefaults = Pick<
  NewContadorResponsavelTarefaRecorrente,
  'id' | 'dataAtribuicao' | 'dataRevogacao' | 'concluida'
>;

type ContadorResponsavelTarefaRecorrenteFormGroupContent = {
  id: FormControl<ContadorResponsavelTarefaRecorrenteFormRawValue['id'] | NewContadorResponsavelTarefaRecorrente['id']>;
  dataAtribuicao: FormControl<ContadorResponsavelTarefaRecorrenteFormRawValue['dataAtribuicao']>;
  dataRevogacao: FormControl<ContadorResponsavelTarefaRecorrenteFormRawValue['dataRevogacao']>;
  concluida: FormControl<ContadorResponsavelTarefaRecorrenteFormRawValue['concluida']>;
  tarefaRecorrenteExecucao: FormControl<ContadorResponsavelTarefaRecorrenteFormRawValue['tarefaRecorrenteExecucao']>;
  contador: FormControl<ContadorResponsavelTarefaRecorrenteFormRawValue['contador']>;
};

export type ContadorResponsavelTarefaRecorrenteFormGroup = FormGroup<ContadorResponsavelTarefaRecorrenteFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContadorResponsavelTarefaRecorrenteFormService {
  createContadorResponsavelTarefaRecorrenteFormGroup(
    contadorResponsavelTarefaRecorrente: ContadorResponsavelTarefaRecorrenteFormGroupInput = { id: null },
  ): ContadorResponsavelTarefaRecorrenteFormGroup {
    const contadorResponsavelTarefaRecorrenteRawValue =
      this.convertContadorResponsavelTarefaRecorrenteToContadorResponsavelTarefaRecorrenteRawValue({
        ...this.getFormDefaults(),
        ...contadorResponsavelTarefaRecorrente,
      });
    return new FormGroup<ContadorResponsavelTarefaRecorrenteFormGroupContent>({
      id: new FormControl(
        { value: contadorResponsavelTarefaRecorrenteRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataAtribuicao: new FormControl(contadorResponsavelTarefaRecorrenteRawValue.dataAtribuicao),
      dataRevogacao: new FormControl(contadorResponsavelTarefaRecorrenteRawValue.dataRevogacao),
      concluida: new FormControl(contadorResponsavelTarefaRecorrenteRawValue.concluida),
      tarefaRecorrenteExecucao: new FormControl(contadorResponsavelTarefaRecorrenteRawValue.tarefaRecorrenteExecucao, {
        validators: [Validators.required],
      }),
      contador: new FormControl(contadorResponsavelTarefaRecorrenteRawValue.contador, {
        validators: [Validators.required],
      }),
    });
  }

  getContadorResponsavelTarefaRecorrente(
    form: ContadorResponsavelTarefaRecorrenteFormGroup,
  ): IContadorResponsavelTarefaRecorrente | NewContadorResponsavelTarefaRecorrente {
    return this.convertContadorResponsavelTarefaRecorrenteRawValueToContadorResponsavelTarefaRecorrente(
      form.getRawValue() as ContadorResponsavelTarefaRecorrenteFormRawValue | NewContadorResponsavelTarefaRecorrenteFormRawValue,
    );
  }

  resetForm(
    form: ContadorResponsavelTarefaRecorrenteFormGroup,
    contadorResponsavelTarefaRecorrente: ContadorResponsavelTarefaRecorrenteFormGroupInput,
  ): void {
    const contadorResponsavelTarefaRecorrenteRawValue =
      this.convertContadorResponsavelTarefaRecorrenteToContadorResponsavelTarefaRecorrenteRawValue({
        ...this.getFormDefaults(),
        ...contadorResponsavelTarefaRecorrente,
      });
    form.reset(
      {
        ...contadorResponsavelTarefaRecorrenteRawValue,
        id: { value: contadorResponsavelTarefaRecorrenteRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContadorResponsavelTarefaRecorrenteFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAtribuicao: currentTime,
      dataRevogacao: currentTime,
      concluida: false,
    };
  }

  private convertContadorResponsavelTarefaRecorrenteRawValueToContadorResponsavelTarefaRecorrente(
    rawContadorResponsavelTarefaRecorrente:
      | ContadorResponsavelTarefaRecorrenteFormRawValue
      | NewContadorResponsavelTarefaRecorrenteFormRawValue,
  ): IContadorResponsavelTarefaRecorrente | NewContadorResponsavelTarefaRecorrente {
    return {
      ...rawContadorResponsavelTarefaRecorrente,
      dataAtribuicao: dayjs(rawContadorResponsavelTarefaRecorrente.dataAtribuicao, DATE_TIME_FORMAT),
      dataRevogacao: dayjs(rawContadorResponsavelTarefaRecorrente.dataRevogacao, DATE_TIME_FORMAT),
    };
  }

  private convertContadorResponsavelTarefaRecorrenteToContadorResponsavelTarefaRecorrenteRawValue(
    contadorResponsavelTarefaRecorrente:
      | IContadorResponsavelTarefaRecorrente
      | (Partial<NewContadorResponsavelTarefaRecorrente> & ContadorResponsavelTarefaRecorrenteFormDefaults),
  ): ContadorResponsavelTarefaRecorrenteFormRawValue | PartialWithRequiredKeyOf<NewContadorResponsavelTarefaRecorrenteFormRawValue> {
    return {
      ...contadorResponsavelTarefaRecorrente,
      dataAtribuicao: contadorResponsavelTarefaRecorrente.dataAtribuicao
        ? contadorResponsavelTarefaRecorrente.dataAtribuicao.format(DATE_TIME_FORMAT)
        : undefined,
      dataRevogacao: contadorResponsavelTarefaRecorrente.dataRevogacao
        ? contadorResponsavelTarefaRecorrente.dataRevogacao.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
