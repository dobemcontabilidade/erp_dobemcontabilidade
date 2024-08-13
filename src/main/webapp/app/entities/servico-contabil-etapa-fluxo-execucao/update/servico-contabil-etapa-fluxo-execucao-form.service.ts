import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IServicoContabilEtapaFluxoExecucao, NewServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IServicoContabilEtapaFluxoExecucao for edit and NewServicoContabilEtapaFluxoExecucaoFormGroupInput for create.
 */
type ServicoContabilEtapaFluxoExecucaoFormGroupInput =
  | IServicoContabilEtapaFluxoExecucao
  | PartialWithRequiredKeyOf<NewServicoContabilEtapaFluxoExecucao>;

type ServicoContabilEtapaFluxoExecucaoFormDefaults = Pick<NewServicoContabilEtapaFluxoExecucao, 'id' | 'feito'>;

type ServicoContabilEtapaFluxoExecucaoFormGroupContent = {
  id: FormControl<IServicoContabilEtapaFluxoExecucao['id'] | NewServicoContabilEtapaFluxoExecucao['id']>;
  ordem: FormControl<IServicoContabilEtapaFluxoExecucao['ordem']>;
  feito: FormControl<IServicoContabilEtapaFluxoExecucao['feito']>;
  prazo: FormControl<IServicoContabilEtapaFluxoExecucao['prazo']>;
  servicoContabil: FormControl<IServicoContabilEtapaFluxoExecucao['servicoContabil']>;
  etapaFluxoExecucao: FormControl<IServicoContabilEtapaFluxoExecucao['etapaFluxoExecucao']>;
};

export type ServicoContabilEtapaFluxoExecucaoFormGroup = FormGroup<ServicoContabilEtapaFluxoExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilEtapaFluxoExecucaoFormService {
  createServicoContabilEtapaFluxoExecucaoFormGroup(
    servicoContabilEtapaFluxoExecucao: ServicoContabilEtapaFluxoExecucaoFormGroupInput = { id: null },
  ): ServicoContabilEtapaFluxoExecucaoFormGroup {
    const servicoContabilEtapaFluxoExecucaoRawValue = {
      ...this.getFormDefaults(),
      ...servicoContabilEtapaFluxoExecucao,
    };
    return new FormGroup<ServicoContabilEtapaFluxoExecucaoFormGroupContent>({
      id: new FormControl(
        { value: servicoContabilEtapaFluxoExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ordem: new FormControl(servicoContabilEtapaFluxoExecucaoRawValue.ordem),
      feito: new FormControl(servicoContabilEtapaFluxoExecucaoRawValue.feito),
      prazo: new FormControl(servicoContabilEtapaFluxoExecucaoRawValue.prazo),
      servicoContabil: new FormControl(servicoContabilEtapaFluxoExecucaoRawValue.servicoContabil, {
        validators: [Validators.required],
      }),
      etapaFluxoExecucao: new FormControl(servicoContabilEtapaFluxoExecucaoRawValue.etapaFluxoExecucao, {
        validators: [Validators.required],
      }),
    });
  }

  getServicoContabilEtapaFluxoExecucao(
    form: ServicoContabilEtapaFluxoExecucaoFormGroup,
  ): IServicoContabilEtapaFluxoExecucao | NewServicoContabilEtapaFluxoExecucao {
    return form.getRawValue() as IServicoContabilEtapaFluxoExecucao | NewServicoContabilEtapaFluxoExecucao;
  }

  resetForm(
    form: ServicoContabilEtapaFluxoExecucaoFormGroup,
    servicoContabilEtapaFluxoExecucao: ServicoContabilEtapaFluxoExecucaoFormGroupInput,
  ): void {
    const servicoContabilEtapaFluxoExecucaoRawValue = { ...this.getFormDefaults(), ...servicoContabilEtapaFluxoExecucao };
    form.reset(
      {
        ...servicoContabilEtapaFluxoExecucaoRawValue,
        id: { value: servicoContabilEtapaFluxoExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServicoContabilEtapaFluxoExecucaoFormDefaults {
    return {
      id: null,
      feito: false,
    };
  }
}
