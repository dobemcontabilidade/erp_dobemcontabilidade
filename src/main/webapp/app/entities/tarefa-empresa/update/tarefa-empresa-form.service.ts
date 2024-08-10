import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITarefaEmpresa, NewTarefaEmpresa } from '../tarefa-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarefaEmpresa for edit and NewTarefaEmpresaFormGroupInput for create.
 */
type TarefaEmpresaFormGroupInput = ITarefaEmpresa | PartialWithRequiredKeyOf<NewTarefaEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITarefaEmpresa | NewTarefaEmpresa> = Omit<T, 'dataHora'> & {
  dataHora?: string | null;
};

type TarefaEmpresaFormRawValue = FormValueOf<ITarefaEmpresa>;

type NewTarefaEmpresaFormRawValue = FormValueOf<NewTarefaEmpresa>;

type TarefaEmpresaFormDefaults = Pick<NewTarefaEmpresa, 'id' | 'dataHora'>;

type TarefaEmpresaFormGroupContent = {
  id: FormControl<TarefaEmpresaFormRawValue['id'] | NewTarefaEmpresa['id']>;
  dataHora: FormControl<TarefaEmpresaFormRawValue['dataHora']>;
  empresa: FormControl<TarefaEmpresaFormRawValue['empresa']>;
  contador: FormControl<TarefaEmpresaFormRawValue['contador']>;
  tarefa: FormControl<TarefaEmpresaFormRawValue['tarefa']>;
};

export type TarefaEmpresaFormGroup = FormGroup<TarefaEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TarefaEmpresaFormService {
  createTarefaEmpresaFormGroup(tarefaEmpresa: TarefaEmpresaFormGroupInput = { id: null }): TarefaEmpresaFormGroup {
    const tarefaEmpresaRawValue = this.convertTarefaEmpresaToTarefaEmpresaRawValue({
      ...this.getFormDefaults(),
      ...tarefaEmpresa,
    });
    return new FormGroup<TarefaEmpresaFormGroupContent>({
      id: new FormControl(
        { value: tarefaEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataHora: new FormControl(tarefaEmpresaRawValue.dataHora),
      empresa: new FormControl(tarefaEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
      contador: new FormControl(tarefaEmpresaRawValue.contador, {
        validators: [Validators.required],
      }),
      tarefa: new FormControl(tarefaEmpresaRawValue.tarefa, {
        validators: [Validators.required],
      }),
    });
  }

  getTarefaEmpresa(form: TarefaEmpresaFormGroup): ITarefaEmpresa | NewTarefaEmpresa {
    return this.convertTarefaEmpresaRawValueToTarefaEmpresa(form.getRawValue() as TarefaEmpresaFormRawValue | NewTarefaEmpresaFormRawValue);
  }

  resetForm(form: TarefaEmpresaFormGroup, tarefaEmpresa: TarefaEmpresaFormGroupInput): void {
    const tarefaEmpresaRawValue = this.convertTarefaEmpresaToTarefaEmpresaRawValue({ ...this.getFormDefaults(), ...tarefaEmpresa });
    form.reset(
      {
        ...tarefaEmpresaRawValue,
        id: { value: tarefaEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TarefaEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataHora: currentTime,
    };
  }

  private convertTarefaEmpresaRawValueToTarefaEmpresa(
    rawTarefaEmpresa: TarefaEmpresaFormRawValue | NewTarefaEmpresaFormRawValue,
  ): ITarefaEmpresa | NewTarefaEmpresa {
    return {
      ...rawTarefaEmpresa,
      dataHora: dayjs(rawTarefaEmpresa.dataHora, DATE_TIME_FORMAT),
    };
  }

  private convertTarefaEmpresaToTarefaEmpresaRawValue(
    tarefaEmpresa: ITarefaEmpresa | (Partial<NewTarefaEmpresa> & TarefaEmpresaFormDefaults),
  ): TarefaEmpresaFormRawValue | PartialWithRequiredKeyOf<NewTarefaEmpresaFormRawValue> {
    return {
      ...tarefaEmpresa,
      dataHora: tarefaEmpresa.dataHora ? tarefaEmpresa.dataHora.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
