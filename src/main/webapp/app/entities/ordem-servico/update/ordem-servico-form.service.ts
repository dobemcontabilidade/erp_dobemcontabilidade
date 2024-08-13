import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IOrdemServico, NewOrdemServico } from '../ordem-servico.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IOrdemServico for edit and NewOrdemServicoFormGroupInput for create.
 */
type OrdemServicoFormGroupInput = IOrdemServico | PartialWithRequiredKeyOf<NewOrdemServico>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IOrdemServico | NewOrdemServico> = Omit<T, 'prazo' | 'dataCriacao' | 'dataHoraCancelamento'> & {
  prazo?: string | null;
  dataCriacao?: string | null;
  dataHoraCancelamento?: string | null;
};

type OrdemServicoFormRawValue = FormValueOf<IOrdemServico>;

type NewOrdemServicoFormRawValue = FormValueOf<NewOrdemServico>;

type OrdemServicoFormDefaults = Pick<NewOrdemServico, 'id' | 'prazo' | 'dataCriacao' | 'dataHoraCancelamento'>;

type OrdemServicoFormGroupContent = {
  id: FormControl<OrdemServicoFormRawValue['id'] | NewOrdemServico['id']>;
  valor: FormControl<OrdemServicoFormRawValue['valor']>;
  prazo: FormControl<OrdemServicoFormRawValue['prazo']>;
  dataCriacao: FormControl<OrdemServicoFormRawValue['dataCriacao']>;
  dataHoraCancelamento: FormControl<OrdemServicoFormRawValue['dataHoraCancelamento']>;
  statusDaOS: FormControl<OrdemServicoFormRawValue['statusDaOS']>;
  descricao: FormControl<OrdemServicoFormRawValue['descricao']>;
  empresa: FormControl<OrdemServicoFormRawValue['empresa']>;
  contador: FormControl<OrdemServicoFormRawValue['contador']>;
  fluxoModelo: FormControl<OrdemServicoFormRawValue['fluxoModelo']>;
};

export type OrdemServicoFormGroup = FormGroup<OrdemServicoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class OrdemServicoFormService {
  createOrdemServicoFormGroup(ordemServico: OrdemServicoFormGroupInput = { id: null }): OrdemServicoFormGroup {
    const ordemServicoRawValue = this.convertOrdemServicoToOrdemServicoRawValue({
      ...this.getFormDefaults(),
      ...ordemServico,
    });
    return new FormGroup<OrdemServicoFormGroupContent>({
      id: new FormControl(
        { value: ordemServicoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      valor: new FormControl(ordemServicoRawValue.valor),
      prazo: new FormControl(ordemServicoRawValue.prazo),
      dataCriacao: new FormControl(ordemServicoRawValue.dataCriacao),
      dataHoraCancelamento: new FormControl(ordemServicoRawValue.dataHoraCancelamento),
      statusDaOS: new FormControl(ordemServicoRawValue.statusDaOS),
      descricao: new FormControl(ordemServicoRawValue.descricao),
      empresa: new FormControl(ordemServicoRawValue.empresa, {
        validators: [Validators.required],
      }),
      contador: new FormControl(ordemServicoRawValue.contador, {
        validators: [Validators.required],
      }),
      fluxoModelo: new FormControl(ordemServicoRawValue.fluxoModelo),
    });
  }

  getOrdemServico(form: OrdemServicoFormGroup): IOrdemServico | NewOrdemServico {
    return this.convertOrdemServicoRawValueToOrdemServico(form.getRawValue() as OrdemServicoFormRawValue | NewOrdemServicoFormRawValue);
  }

  resetForm(form: OrdemServicoFormGroup, ordemServico: OrdemServicoFormGroupInput): void {
    const ordemServicoRawValue = this.convertOrdemServicoToOrdemServicoRawValue({ ...this.getFormDefaults(), ...ordemServico });
    form.reset(
      {
        ...ordemServicoRawValue,
        id: { value: ordemServicoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): OrdemServicoFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      prazo: currentTime,
      dataCriacao: currentTime,
      dataHoraCancelamento: currentTime,
    };
  }

  private convertOrdemServicoRawValueToOrdemServico(
    rawOrdemServico: OrdemServicoFormRawValue | NewOrdemServicoFormRawValue,
  ): IOrdemServico | NewOrdemServico {
    return {
      ...rawOrdemServico,
      prazo: dayjs(rawOrdemServico.prazo, DATE_TIME_FORMAT),
      dataCriacao: dayjs(rawOrdemServico.dataCriacao, DATE_TIME_FORMAT),
      dataHoraCancelamento: dayjs(rawOrdemServico.dataHoraCancelamento, DATE_TIME_FORMAT),
    };
  }

  private convertOrdemServicoToOrdemServicoRawValue(
    ordemServico: IOrdemServico | (Partial<NewOrdemServico> & OrdemServicoFormDefaults),
  ): OrdemServicoFormRawValue | PartialWithRequiredKeyOf<NewOrdemServicoFormRawValue> {
    return {
      ...ordemServico,
      prazo: ordemServico.prazo ? ordemServico.prazo.format(DATE_TIME_FORMAT) : undefined,
      dataCriacao: ordemServico.dataCriacao ? ordemServico.dataCriacao.format(DATE_TIME_FORMAT) : undefined,
      dataHoraCancelamento: ordemServico.dataHoraCancelamento ? ordemServico.dataHoraCancelamento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
