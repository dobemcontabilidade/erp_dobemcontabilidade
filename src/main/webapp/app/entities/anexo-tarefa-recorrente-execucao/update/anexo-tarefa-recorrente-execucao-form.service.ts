import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAnexoTarefaRecorrenteExecucao, NewAnexoTarefaRecorrenteExecucao } from '../anexo-tarefa-recorrente-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoTarefaRecorrenteExecucao for edit and NewAnexoTarefaRecorrenteExecucaoFormGroupInput for create.
 */
type AnexoTarefaRecorrenteExecucaoFormGroupInput =
  | IAnexoTarefaRecorrenteExecucao
  | PartialWithRequiredKeyOf<NewAnexoTarefaRecorrenteExecucao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAnexoTarefaRecorrenteExecucao | NewAnexoTarefaRecorrenteExecucao> = Omit<T, 'dataHoraUpload'> & {
  dataHoraUpload?: string | null;
};

type AnexoTarefaRecorrenteExecucaoFormRawValue = FormValueOf<IAnexoTarefaRecorrenteExecucao>;

type NewAnexoTarefaRecorrenteExecucaoFormRawValue = FormValueOf<NewAnexoTarefaRecorrenteExecucao>;

type AnexoTarefaRecorrenteExecucaoFormDefaults = Pick<NewAnexoTarefaRecorrenteExecucao, 'id' | 'dataHoraUpload'>;

type AnexoTarefaRecorrenteExecucaoFormGroupContent = {
  id: FormControl<AnexoTarefaRecorrenteExecucaoFormRawValue['id'] | NewAnexoTarefaRecorrenteExecucao['id']>;
  url: FormControl<AnexoTarefaRecorrenteExecucaoFormRawValue['url']>;
  descricao: FormControl<AnexoTarefaRecorrenteExecucaoFormRawValue['descricao']>;
  dataHoraUpload: FormControl<AnexoTarefaRecorrenteExecucaoFormRawValue['dataHoraUpload']>;
  tarefaRecorrenteExecucao: FormControl<AnexoTarefaRecorrenteExecucaoFormRawValue['tarefaRecorrenteExecucao']>;
};

export type AnexoTarefaRecorrenteExecucaoFormGroup = FormGroup<AnexoTarefaRecorrenteExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoTarefaRecorrenteExecucaoFormService {
  createAnexoTarefaRecorrenteExecucaoFormGroup(
    anexoTarefaRecorrenteExecucao: AnexoTarefaRecorrenteExecucaoFormGroupInput = { id: null },
  ): AnexoTarefaRecorrenteExecucaoFormGroup {
    const anexoTarefaRecorrenteExecucaoRawValue = this.convertAnexoTarefaRecorrenteExecucaoToAnexoTarefaRecorrenteExecucaoRawValue({
      ...this.getFormDefaults(),
      ...anexoTarefaRecorrenteExecucao,
    });
    return new FormGroup<AnexoTarefaRecorrenteExecucaoFormGroupContent>({
      id: new FormControl(
        { value: anexoTarefaRecorrenteExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      url: new FormControl(anexoTarefaRecorrenteExecucaoRawValue.url),
      descricao: new FormControl(anexoTarefaRecorrenteExecucaoRawValue.descricao),
      dataHoraUpload: new FormControl(anexoTarefaRecorrenteExecucaoRawValue.dataHoraUpload),
      tarefaRecorrenteExecucao: new FormControl(anexoTarefaRecorrenteExecucaoRawValue.tarefaRecorrenteExecucao, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoTarefaRecorrenteExecucao(
    form: AnexoTarefaRecorrenteExecucaoFormGroup,
  ): IAnexoTarefaRecorrenteExecucao | NewAnexoTarefaRecorrenteExecucao {
    return this.convertAnexoTarefaRecorrenteExecucaoRawValueToAnexoTarefaRecorrenteExecucao(
      form.getRawValue() as AnexoTarefaRecorrenteExecucaoFormRawValue | NewAnexoTarefaRecorrenteExecucaoFormRawValue,
    );
  }

  resetForm(
    form: AnexoTarefaRecorrenteExecucaoFormGroup,
    anexoTarefaRecorrenteExecucao: AnexoTarefaRecorrenteExecucaoFormGroupInput,
  ): void {
    const anexoTarefaRecorrenteExecucaoRawValue = this.convertAnexoTarefaRecorrenteExecucaoToAnexoTarefaRecorrenteExecucaoRawValue({
      ...this.getFormDefaults(),
      ...anexoTarefaRecorrenteExecucao,
    });
    form.reset(
      {
        ...anexoTarefaRecorrenteExecucaoRawValue,
        id: { value: anexoTarefaRecorrenteExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoTarefaRecorrenteExecucaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataHoraUpload: currentTime,
    };
  }

  private convertAnexoTarefaRecorrenteExecucaoRawValueToAnexoTarefaRecorrenteExecucao(
    rawAnexoTarefaRecorrenteExecucao: AnexoTarefaRecorrenteExecucaoFormRawValue | NewAnexoTarefaRecorrenteExecucaoFormRawValue,
  ): IAnexoTarefaRecorrenteExecucao | NewAnexoTarefaRecorrenteExecucao {
    return {
      ...rawAnexoTarefaRecorrenteExecucao,
      dataHoraUpload: dayjs(rawAnexoTarefaRecorrenteExecucao.dataHoraUpload, DATE_TIME_FORMAT),
    };
  }

  private convertAnexoTarefaRecorrenteExecucaoToAnexoTarefaRecorrenteExecucaoRawValue(
    anexoTarefaRecorrenteExecucao:
      | IAnexoTarefaRecorrenteExecucao
      | (Partial<NewAnexoTarefaRecorrenteExecucao> & AnexoTarefaRecorrenteExecucaoFormDefaults),
  ): AnexoTarefaRecorrenteExecucaoFormRawValue | PartialWithRequiredKeyOf<NewAnexoTarefaRecorrenteExecucaoFormRawValue> {
    return {
      ...anexoTarefaRecorrenteExecucao,
      dataHoraUpload: anexoTarefaRecorrenteExecucao.dataHoraUpload
        ? anexoTarefaRecorrenteExecucao.dataHoraUpload.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
