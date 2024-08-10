import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITarefa, NewTarefa } from '../tarefa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarefa for edit and NewTarefaFormGroupInput for create.
 */
type TarefaFormGroupInput = ITarefa | PartialWithRequiredKeyOf<NewTarefa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITarefa | NewTarefa> = Omit<T, 'dataLegal'> & {
  dataLegal?: string | null;
};

type TarefaFormRawValue = FormValueOf<ITarefa>;

type NewTarefaFormRawValue = FormValueOf<NewTarefa>;

type TarefaFormDefaults = Pick<NewTarefa, 'id' | 'diaUtil' | 'notificarCliente' | 'geraMulta' | 'exibirEmpresa' | 'dataLegal'>;

type TarefaFormGroupContent = {
  id: FormControl<TarefaFormRawValue['id'] | NewTarefa['id']>;
  titulo: FormControl<TarefaFormRawValue['titulo']>;
  numeroDias: FormControl<TarefaFormRawValue['numeroDias']>;
  diaUtil: FormControl<TarefaFormRawValue['diaUtil']>;
  valor: FormControl<TarefaFormRawValue['valor']>;
  notificarCliente: FormControl<TarefaFormRawValue['notificarCliente']>;
  geraMulta: FormControl<TarefaFormRawValue['geraMulta']>;
  exibirEmpresa: FormControl<TarefaFormRawValue['exibirEmpresa']>;
  dataLegal: FormControl<TarefaFormRawValue['dataLegal']>;
  pontos: FormControl<TarefaFormRawValue['pontos']>;
  tipoTarefa: FormControl<TarefaFormRawValue['tipoTarefa']>;
  esfera: FormControl<TarefaFormRawValue['esfera']>;
  frequencia: FormControl<TarefaFormRawValue['frequencia']>;
  competencia: FormControl<TarefaFormRawValue['competencia']>;
};

export type TarefaFormGroup = FormGroup<TarefaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TarefaFormService {
  createTarefaFormGroup(tarefa: TarefaFormGroupInput = { id: null }): TarefaFormGroup {
    const tarefaRawValue = this.convertTarefaToTarefaRawValue({
      ...this.getFormDefaults(),
      ...tarefa,
    });
    return new FormGroup<TarefaFormGroupContent>({
      id: new FormControl(
        { value: tarefaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      titulo: new FormControl(tarefaRawValue.titulo),
      numeroDias: new FormControl(tarefaRawValue.numeroDias),
      diaUtil: new FormControl(tarefaRawValue.diaUtil),
      valor: new FormControl(tarefaRawValue.valor),
      notificarCliente: new FormControl(tarefaRawValue.notificarCliente),
      geraMulta: new FormControl(tarefaRawValue.geraMulta),
      exibirEmpresa: new FormControl(tarefaRawValue.exibirEmpresa),
      dataLegal: new FormControl(tarefaRawValue.dataLegal),
      pontos: new FormControl(tarefaRawValue.pontos),
      tipoTarefa: new FormControl(tarefaRawValue.tipoTarefa),
      esfera: new FormControl(tarefaRawValue.esfera, {
        validators: [Validators.required],
      }),
      frequencia: new FormControl(tarefaRawValue.frequencia, {
        validators: [Validators.required],
      }),
      competencia: new FormControl(tarefaRawValue.competencia, {
        validators: [Validators.required],
      }),
    });
  }

  getTarefa(form: TarefaFormGroup): ITarefa | NewTarefa {
    return this.convertTarefaRawValueToTarefa(form.getRawValue() as TarefaFormRawValue | NewTarefaFormRawValue);
  }

  resetForm(form: TarefaFormGroup, tarefa: TarefaFormGroupInput): void {
    const tarefaRawValue = this.convertTarefaToTarefaRawValue({ ...this.getFormDefaults(), ...tarefa });
    form.reset(
      {
        ...tarefaRawValue,
        id: { value: tarefaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TarefaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      diaUtil: false,
      notificarCliente: false,
      geraMulta: false,
      exibirEmpresa: false,
      dataLegal: currentTime,
    };
  }

  private convertTarefaRawValueToTarefa(rawTarefa: TarefaFormRawValue | NewTarefaFormRawValue): ITarefa | NewTarefa {
    return {
      ...rawTarefa,
      dataLegal: dayjs(rawTarefa.dataLegal, DATE_TIME_FORMAT),
    };
  }

  private convertTarefaToTarefaRawValue(
    tarefa: ITarefa | (Partial<NewTarefa> & TarefaFormDefaults),
  ): TarefaFormRawValue | PartialWithRequiredKeyOf<NewTarefaFormRawValue> {
    return {
      ...tarefa,
      dataLegal: tarefa.dataLegal ? tarefa.dataLegal.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
