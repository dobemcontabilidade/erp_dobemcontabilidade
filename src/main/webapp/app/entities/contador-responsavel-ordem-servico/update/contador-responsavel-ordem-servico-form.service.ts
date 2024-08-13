import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IContadorResponsavelOrdemServico, NewContadorResponsavelOrdemServico } from '../contador-responsavel-ordem-servico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContadorResponsavelOrdemServico for edit and NewContadorResponsavelOrdemServicoFormGroupInput for create.
 */
type ContadorResponsavelOrdemServicoFormGroupInput =
  | IContadorResponsavelOrdemServico
  | PartialWithRequiredKeyOf<NewContadorResponsavelOrdemServico>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IContadorResponsavelOrdemServico | NewContadorResponsavelOrdemServico> = Omit<
  T,
  'dataAtribuicao' | 'dataRevogacao'
> & {
  dataAtribuicao?: string | null;
  dataRevogacao?: string | null;
};

type ContadorResponsavelOrdemServicoFormRawValue = FormValueOf<IContadorResponsavelOrdemServico>;

type NewContadorResponsavelOrdemServicoFormRawValue = FormValueOf<NewContadorResponsavelOrdemServico>;

type ContadorResponsavelOrdemServicoFormDefaults = Pick<NewContadorResponsavelOrdemServico, 'id' | 'dataAtribuicao' | 'dataRevogacao'>;

type ContadorResponsavelOrdemServicoFormGroupContent = {
  id: FormControl<ContadorResponsavelOrdemServicoFormRawValue['id'] | NewContadorResponsavelOrdemServico['id']>;
  dataAtribuicao: FormControl<ContadorResponsavelOrdemServicoFormRawValue['dataAtribuicao']>;
  dataRevogacao: FormControl<ContadorResponsavelOrdemServicoFormRawValue['dataRevogacao']>;
  tarefaOrdemServicoExecucao: FormControl<ContadorResponsavelOrdemServicoFormRawValue['tarefaOrdemServicoExecucao']>;
  contador: FormControl<ContadorResponsavelOrdemServicoFormRawValue['contador']>;
};

export type ContadorResponsavelOrdemServicoFormGroup = FormGroup<ContadorResponsavelOrdemServicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContadorResponsavelOrdemServicoFormService {
  createContadorResponsavelOrdemServicoFormGroup(
    contadorResponsavelOrdemServico: ContadorResponsavelOrdemServicoFormGroupInput = { id: null },
  ): ContadorResponsavelOrdemServicoFormGroup {
    const contadorResponsavelOrdemServicoRawValue = this.convertContadorResponsavelOrdemServicoToContadorResponsavelOrdemServicoRawValue({
      ...this.getFormDefaults(),
      ...contadorResponsavelOrdemServico,
    });
    return new FormGroup<ContadorResponsavelOrdemServicoFormGroupContent>({
      id: new FormControl(
        { value: contadorResponsavelOrdemServicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      dataAtribuicao: new FormControl(contadorResponsavelOrdemServicoRawValue.dataAtribuicao),
      dataRevogacao: new FormControl(contadorResponsavelOrdemServicoRawValue.dataRevogacao),
      tarefaOrdemServicoExecucao: new FormControl(contadorResponsavelOrdemServicoRawValue.tarefaOrdemServicoExecucao, {
        validators: [Validators.required],
      }),
      contador: new FormControl(contadorResponsavelOrdemServicoRawValue.contador, {
        validators: [Validators.required],
      }),
    });
  }

  getContadorResponsavelOrdemServico(
    form: ContadorResponsavelOrdemServicoFormGroup,
  ): IContadorResponsavelOrdemServico | NewContadorResponsavelOrdemServico {
    return this.convertContadorResponsavelOrdemServicoRawValueToContadorResponsavelOrdemServico(
      form.getRawValue() as ContadorResponsavelOrdemServicoFormRawValue | NewContadorResponsavelOrdemServicoFormRawValue,
    );
  }

  resetForm(
    form: ContadorResponsavelOrdemServicoFormGroup,
    contadorResponsavelOrdemServico: ContadorResponsavelOrdemServicoFormGroupInput,
  ): void {
    const contadorResponsavelOrdemServicoRawValue = this.convertContadorResponsavelOrdemServicoToContadorResponsavelOrdemServicoRawValue({
      ...this.getFormDefaults(),
      ...contadorResponsavelOrdemServico,
    });
    form.reset(
      {
        ...contadorResponsavelOrdemServicoRawValue,
        id: { value: contadorResponsavelOrdemServicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContadorResponsavelOrdemServicoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataAtribuicao: currentTime,
      dataRevogacao: currentTime,
    };
  }

  private convertContadorResponsavelOrdemServicoRawValueToContadorResponsavelOrdemServico(
    rawContadorResponsavelOrdemServico: ContadorResponsavelOrdemServicoFormRawValue | NewContadorResponsavelOrdemServicoFormRawValue,
  ): IContadorResponsavelOrdemServico | NewContadorResponsavelOrdemServico {
    return {
      ...rawContadorResponsavelOrdemServico,
      dataAtribuicao: dayjs(rawContadorResponsavelOrdemServico.dataAtribuicao, DATE_TIME_FORMAT),
      dataRevogacao: dayjs(rawContadorResponsavelOrdemServico.dataRevogacao, DATE_TIME_FORMAT),
    };
  }

  private convertContadorResponsavelOrdemServicoToContadorResponsavelOrdemServicoRawValue(
    contadorResponsavelOrdemServico:
      | IContadorResponsavelOrdemServico
      | (Partial<NewContadorResponsavelOrdemServico> & ContadorResponsavelOrdemServicoFormDefaults),
  ): ContadorResponsavelOrdemServicoFormRawValue | PartialWithRequiredKeyOf<NewContadorResponsavelOrdemServicoFormRawValue> {
    return {
      ...contadorResponsavelOrdemServico,
      dataAtribuicao: contadorResponsavelOrdemServico.dataAtribuicao
        ? contadorResponsavelOrdemServico.dataAtribuicao.format(DATE_TIME_FORMAT)
        : undefined,
      dataRevogacao: contadorResponsavelOrdemServico.dataRevogacao
        ? contadorResponsavelOrdemServico.dataRevogacao.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
