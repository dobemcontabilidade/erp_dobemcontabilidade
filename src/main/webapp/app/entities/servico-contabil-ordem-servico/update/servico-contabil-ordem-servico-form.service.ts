import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IServicoContabilOrdemServico, NewServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServicoContabilOrdemServico for edit and NewServicoContabilOrdemServicoFormGroupInput for create.
 */
type ServicoContabilOrdemServicoFormGroupInput = IServicoContabilOrdemServico | PartialWithRequiredKeyOf<NewServicoContabilOrdemServico>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IServicoContabilOrdemServico | NewServicoContabilOrdemServico> = Omit<T, 'dataAdmin' | 'dataLegal'> & {
  dataAdmin?: string | null;
  dataLegal?: string | null;
};

type ServicoContabilOrdemServicoFormRawValue = FormValueOf<IServicoContabilOrdemServico>;

type NewServicoContabilOrdemServicoFormRawValue = FormValueOf<NewServicoContabilOrdemServico>;

type ServicoContabilOrdemServicoFormDefaults = Pick<NewServicoContabilOrdemServico, 'id' | 'dataAdmin' | 'dataLegal'>;

type ServicoContabilOrdemServicoFormGroupContent = {
  id: FormControl<ServicoContabilOrdemServicoFormRawValue['id'] | NewServicoContabilOrdemServico['id']>;
  dataAdmin: FormControl<ServicoContabilOrdemServicoFormRawValue['dataAdmin']>;
  dataLegal: FormControl<ServicoContabilOrdemServicoFormRawValue['dataLegal']>;
  servicoContabil: FormControl<ServicoContabilOrdemServicoFormRawValue['servicoContabil']>;
  ordemServico: FormControl<ServicoContabilOrdemServicoFormRawValue['ordemServico']>;
};

export type ServicoContabilOrdemServicoFormGroup = FormGroup<ServicoContabilOrdemServicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilOrdemServicoFormService {
  createServicoContabilOrdemServicoFormGroup(
    servicoContabilOrdemServico: ServicoContabilOrdemServicoFormGroupInput = { id: null },
  ): ServicoContabilOrdemServicoFormGroup {
    const servicoContabilOrdemServicoRawValue = this.convertServicoContabilOrdemServicoToServicoContabilOrdemServicoRawValue({
      ...this.getFormDefaults(),
      ...servicoContabilOrdemServico,
    });
    return new FormGroup<ServicoContabilOrdemServicoFormGroupContent>({
      id: new FormControl(
        { value: servicoContabilOrdemServicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataAdmin: new FormControl(servicoContabilOrdemServicoRawValue.dataAdmin),
      dataLegal: new FormControl(servicoContabilOrdemServicoRawValue.dataLegal),
      servicoContabil: new FormControl(servicoContabilOrdemServicoRawValue.servicoContabil, {
        validators: [Validators.required],
      }),
      ordemServico: new FormControl(servicoContabilOrdemServicoRawValue.ordemServico, {
        validators: [Validators.required],
      }),
    });
  }

  getServicoContabilOrdemServico(
    form: ServicoContabilOrdemServicoFormGroup,
  ): IServicoContabilOrdemServico | NewServicoContabilOrdemServico {
    return this.convertServicoContabilOrdemServicoRawValueToServicoContabilOrdemServico(
      form.getRawValue() as ServicoContabilOrdemServicoFormRawValue | NewServicoContabilOrdemServicoFormRawValue,
    );
  }

  resetForm(form: ServicoContabilOrdemServicoFormGroup, servicoContabilOrdemServico: ServicoContabilOrdemServicoFormGroupInput): void {
    const servicoContabilOrdemServicoRawValue = this.convertServicoContabilOrdemServicoToServicoContabilOrdemServicoRawValue({
      ...this.getFormDefaults(),
      ...servicoContabilOrdemServico,
    });
    form.reset(
      {
        ...servicoContabilOrdemServicoRawValue,
        id: { value: servicoContabilOrdemServicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServicoContabilOrdemServicoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAdmin: currentTime,
      dataLegal: currentTime,
    };
  }

  private convertServicoContabilOrdemServicoRawValueToServicoContabilOrdemServico(
    rawServicoContabilOrdemServico: ServicoContabilOrdemServicoFormRawValue | NewServicoContabilOrdemServicoFormRawValue,
  ): IServicoContabilOrdemServico | NewServicoContabilOrdemServico {
    return {
      ...rawServicoContabilOrdemServico,
      dataAdmin: dayjs(rawServicoContabilOrdemServico.dataAdmin, DATE_TIME_FORMAT),
      dataLegal: dayjs(rawServicoContabilOrdemServico.dataLegal, DATE_TIME_FORMAT),
    };
  }

  private convertServicoContabilOrdemServicoToServicoContabilOrdemServicoRawValue(
    servicoContabilOrdemServico:
      | IServicoContabilOrdemServico
      | (Partial<NewServicoContabilOrdemServico> & ServicoContabilOrdemServicoFormDefaults),
  ): ServicoContabilOrdemServicoFormRawValue | PartialWithRequiredKeyOf<NewServicoContabilOrdemServicoFormRawValue> {
    return {
      ...servicoContabilOrdemServico,
      dataAdmin: servicoContabilOrdemServico.dataAdmin ? servicoContabilOrdemServico.dataAdmin.format(DATE_TIME_FORMAT) : undefined,
      dataLegal: servicoContabilOrdemServico.dataLegal ? servicoContabilOrdemServico.dataLegal.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
