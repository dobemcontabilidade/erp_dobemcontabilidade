import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { ITarefaEmpresaModelo, NewTarefaEmpresaModelo } from '../tarefa-empresa-modelo.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ITarefaEmpresaModelo for edit and NewTarefaEmpresaModeloFormGroupInput for create.
 */
type TarefaEmpresaModeloFormGroupInput = ITarefaEmpresaModelo | PartialWithRequiredKeyOf<NewTarefaEmpresaModelo>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends ITarefaEmpresaModelo | NewTarefaEmpresaModelo> = Omit<T, 'dataAdmin' | 'dataLegal'> & {
  dataAdmin?: string | null;
  dataLegal?: string | null;
};

type TarefaEmpresaModeloFormRawValue = FormValueOf<ITarefaEmpresaModelo>;

type NewTarefaEmpresaModeloFormRawValue = FormValueOf<NewTarefaEmpresaModelo>;

type TarefaEmpresaModeloFormDefaults = Pick<NewTarefaEmpresaModelo, 'id' | 'dataAdmin' | 'dataLegal'>;

type TarefaEmpresaModeloFormGroupContent = {
  id: FormControl<TarefaEmpresaModeloFormRawValue['id'] | NewTarefaEmpresaModelo['id']>;
  dataAdmin: FormControl<TarefaEmpresaModeloFormRawValue['dataAdmin']>;
  dataLegal: FormControl<TarefaEmpresaModeloFormRawValue['dataLegal']>;
  empresaModelo: FormControl<TarefaEmpresaModeloFormRawValue['empresaModelo']>;
  servicoContabil: FormControl<TarefaEmpresaModeloFormRawValue['servicoContabil']>;
};

export type TarefaEmpresaModeloFormGroup = FormGroup<TarefaEmpresaModeloFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class TarefaEmpresaModeloFormService {
  createTarefaEmpresaModeloFormGroup(tarefaEmpresaModelo: TarefaEmpresaModeloFormGroupInput = { id: null }): TarefaEmpresaModeloFormGroup {
    const tarefaEmpresaModeloRawValue = this.convertTarefaEmpresaModeloToTarefaEmpresaModeloRawValue({
      ...this.getFormDefaults(),
      ...tarefaEmpresaModelo,
    });
    return new FormGroup<TarefaEmpresaModeloFormGroupContent>({
      id: new FormControl(
        { value: tarefaEmpresaModeloRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataAdmin: new FormControl(tarefaEmpresaModeloRawValue.dataAdmin),
      dataLegal: new FormControl(tarefaEmpresaModeloRawValue.dataLegal),
      empresaModelo: new FormControl(tarefaEmpresaModeloRawValue.empresaModelo, {
        validators: [Validators.required],
      }),
      servicoContabil: new FormControl(tarefaEmpresaModeloRawValue.servicoContabil, {
        validators: [Validators.required],
      }),
    });
  }

  getTarefaEmpresaModelo(form: TarefaEmpresaModeloFormGroup): ITarefaEmpresaModelo | NewTarefaEmpresaModelo {
    return this.convertTarefaEmpresaModeloRawValueToTarefaEmpresaModelo(
      form.getRawValue() as TarefaEmpresaModeloFormRawValue | NewTarefaEmpresaModeloFormRawValue,
    );
  }

  resetForm(form: TarefaEmpresaModeloFormGroup, tarefaEmpresaModelo: TarefaEmpresaModeloFormGroupInput): void {
    const tarefaEmpresaModeloRawValue = this.convertTarefaEmpresaModeloToTarefaEmpresaModeloRawValue({
      ...this.getFormDefaults(),
      ...tarefaEmpresaModelo,
    });
    form.reset(
      {
        ...tarefaEmpresaModeloRawValue,
        id: { value: tarefaEmpresaModeloRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): TarefaEmpresaModeloFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAdmin: currentTime,
      dataLegal: currentTime,
    };
  }

  private convertTarefaEmpresaModeloRawValueToTarefaEmpresaModelo(
    rawTarefaEmpresaModelo: TarefaEmpresaModeloFormRawValue | NewTarefaEmpresaModeloFormRawValue,
  ): ITarefaEmpresaModelo | NewTarefaEmpresaModelo {
    return {
      ...rawTarefaEmpresaModelo,
      dataAdmin: dayjs(rawTarefaEmpresaModelo.dataAdmin, DATE_TIME_FORMAT),
      dataLegal: dayjs(rawTarefaEmpresaModelo.dataLegal, DATE_TIME_FORMAT),
    };
  }

  private convertTarefaEmpresaModeloToTarefaEmpresaModeloRawValue(
    tarefaEmpresaModelo: ITarefaEmpresaModelo | (Partial<NewTarefaEmpresaModelo> & TarefaEmpresaModeloFormDefaults),
  ): TarefaEmpresaModeloFormRawValue | PartialWithRequiredKeyOf<NewTarefaEmpresaModeloFormRawValue> {
    return {
      ...tarefaEmpresaModelo,
      dataAdmin: tarefaEmpresaModelo.dataAdmin ? tarefaEmpresaModelo.dataAdmin.format(DATE_TIME_FORMAT) : undefined,
      dataLegal: tarefaEmpresaModelo.dataLegal ? tarefaEmpresaModelo.dataLegal.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
