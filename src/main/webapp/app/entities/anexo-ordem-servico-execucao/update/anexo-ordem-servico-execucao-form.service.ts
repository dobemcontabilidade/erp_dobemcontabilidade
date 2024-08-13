import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAnexoOrdemServicoExecucao, NewAnexoOrdemServicoExecucao } from '../anexo-ordem-servico-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAnexoOrdemServicoExecucao for edit and NewAnexoOrdemServicoExecucaoFormGroupInput for create.
 */
type AnexoOrdemServicoExecucaoFormGroupInput = IAnexoOrdemServicoExecucao | PartialWithRequiredKeyOf<NewAnexoOrdemServicoExecucao>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAnexoOrdemServicoExecucao | NewAnexoOrdemServicoExecucao> = Omit<T, 'dataHoraUpload'> & {
  dataHoraUpload?: string | null;
};

type AnexoOrdemServicoExecucaoFormRawValue = FormValueOf<IAnexoOrdemServicoExecucao>;

type NewAnexoOrdemServicoExecucaoFormRawValue = FormValueOf<NewAnexoOrdemServicoExecucao>;

type AnexoOrdemServicoExecucaoFormDefaults = Pick<NewAnexoOrdemServicoExecucao, 'id' | 'dataHoraUpload'>;

type AnexoOrdemServicoExecucaoFormGroupContent = {
  id: FormControl<AnexoOrdemServicoExecucaoFormRawValue['id'] | NewAnexoOrdemServicoExecucao['id']>;
  url: FormControl<AnexoOrdemServicoExecucaoFormRawValue['url']>;
  descricao: FormControl<AnexoOrdemServicoExecucaoFormRawValue['descricao']>;
  dataHoraUpload: FormControl<AnexoOrdemServicoExecucaoFormRawValue['dataHoraUpload']>;
  tarefaOrdemServicoExecucao: FormControl<AnexoOrdemServicoExecucaoFormRawValue['tarefaOrdemServicoExecucao']>;
};

export type AnexoOrdemServicoExecucaoFormGroup = FormGroup<AnexoOrdemServicoExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AnexoOrdemServicoExecucaoFormService {
  createAnexoOrdemServicoExecucaoFormGroup(
    anexoOrdemServicoExecucao: AnexoOrdemServicoExecucaoFormGroupInput = { id: null },
  ): AnexoOrdemServicoExecucaoFormGroup {
    const anexoOrdemServicoExecucaoRawValue = this.convertAnexoOrdemServicoExecucaoToAnexoOrdemServicoExecucaoRawValue({
      ...this.getFormDefaults(),
      ...anexoOrdemServicoExecucao,
    });
    return new FormGroup<AnexoOrdemServicoExecucaoFormGroupContent>({
      id: new FormControl(
        { value: anexoOrdemServicoExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      url: new FormControl(anexoOrdemServicoExecucaoRawValue.url),
      descricao: new FormControl(anexoOrdemServicoExecucaoRawValue.descricao),
      dataHoraUpload: new FormControl(anexoOrdemServicoExecucaoRawValue.dataHoraUpload),
      tarefaOrdemServicoExecucao: new FormControl(anexoOrdemServicoExecucaoRawValue.tarefaOrdemServicoExecucao, {
        validators: [Validators.required],
      }),
    });
  }

  getAnexoOrdemServicoExecucao(form: AnexoOrdemServicoExecucaoFormGroup): IAnexoOrdemServicoExecucao | NewAnexoOrdemServicoExecucao {
    return this.convertAnexoOrdemServicoExecucaoRawValueToAnexoOrdemServicoExecucao(
      form.getRawValue() as AnexoOrdemServicoExecucaoFormRawValue | NewAnexoOrdemServicoExecucaoFormRawValue,
    );
  }

  resetForm(form: AnexoOrdemServicoExecucaoFormGroup, anexoOrdemServicoExecucao: AnexoOrdemServicoExecucaoFormGroupInput): void {
    const anexoOrdemServicoExecucaoRawValue = this.convertAnexoOrdemServicoExecucaoToAnexoOrdemServicoExecucaoRawValue({
      ...this.getFormDefaults(),
      ...anexoOrdemServicoExecucao,
    });
    form.reset(
      {
        ...anexoOrdemServicoExecucaoRawValue,
        id: { value: anexoOrdemServicoExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AnexoOrdemServicoExecucaoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataHoraUpload: currentTime,
    };
  }

  private convertAnexoOrdemServicoExecucaoRawValueToAnexoOrdemServicoExecucao(
    rawAnexoOrdemServicoExecucao: AnexoOrdemServicoExecucaoFormRawValue | NewAnexoOrdemServicoExecucaoFormRawValue,
  ): IAnexoOrdemServicoExecucao | NewAnexoOrdemServicoExecucao {
    return {
      ...rawAnexoOrdemServicoExecucao,
      dataHoraUpload: dayjs(rawAnexoOrdemServicoExecucao.dataHoraUpload, DATE_TIME_FORMAT),
    };
  }

  private convertAnexoOrdemServicoExecucaoToAnexoOrdemServicoExecucaoRawValue(
    anexoOrdemServicoExecucao: IAnexoOrdemServicoExecucao | (Partial<NewAnexoOrdemServicoExecucao> & AnexoOrdemServicoExecucaoFormDefaults),
  ): AnexoOrdemServicoExecucaoFormRawValue | PartialWithRequiredKeyOf<NewAnexoOrdemServicoExecucaoFormRawValue> {
    return {
      ...anexoOrdemServicoExecucao,
      dataHoraUpload: anexoOrdemServicoExecucao.dataHoraUpload
        ? anexoOrdemServicoExecucao.dataHoraUpload.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
