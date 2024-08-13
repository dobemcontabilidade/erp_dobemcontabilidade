import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEtapaFluxoExecucao, NewEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEtapaFluxoExecucao for edit and NewEtapaFluxoExecucaoFormGroupInput for create.
 */
type EtapaFluxoExecucaoFormGroupInput = IEtapaFluxoExecucao | PartialWithRequiredKeyOf<NewEtapaFluxoExecucao>;

type EtapaFluxoExecucaoFormDefaults = Pick<NewEtapaFluxoExecucao, 'id' | 'feito' | 'agendada'>;

type EtapaFluxoExecucaoFormGroupContent = {
  id: FormControl<IEtapaFluxoExecucao['id'] | NewEtapaFluxoExecucao['id']>;
  nome: FormControl<IEtapaFluxoExecucao['nome']>;
  descricao: FormControl<IEtapaFluxoExecucao['descricao']>;
  feito: FormControl<IEtapaFluxoExecucao['feito']>;
  ordem: FormControl<IEtapaFluxoExecucao['ordem']>;
  agendada: FormControl<IEtapaFluxoExecucao['agendada']>;
  ordemServico: FormControl<IEtapaFluxoExecucao['ordemServico']>;
};

export type EtapaFluxoExecucaoFormGroup = FormGroup<EtapaFluxoExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EtapaFluxoExecucaoFormService {
  createEtapaFluxoExecucaoFormGroup(etapaFluxoExecucao: EtapaFluxoExecucaoFormGroupInput = { id: null }): EtapaFluxoExecucaoFormGroup {
    const etapaFluxoExecucaoRawValue = {
      ...this.getFormDefaults(),
      ...etapaFluxoExecucao,
    };
    return new FormGroup<EtapaFluxoExecucaoFormGroupContent>({
      id: new FormControl(
        { value: etapaFluxoExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(etapaFluxoExecucaoRawValue.nome),
      descricao: new FormControl(etapaFluxoExecucaoRawValue.descricao),
      feito: new FormControl(etapaFluxoExecucaoRawValue.feito),
      ordem: new FormControl(etapaFluxoExecucaoRawValue.ordem),
      agendada: new FormControl(etapaFluxoExecucaoRawValue.agendada),
      ordemServico: new FormControl(etapaFluxoExecucaoRawValue.ordemServico, {
        validators: [Validators.required],
      }),
    });
  }

  getEtapaFluxoExecucao(form: EtapaFluxoExecucaoFormGroup): IEtapaFluxoExecucao | NewEtapaFluxoExecucao {
    return form.getRawValue() as IEtapaFluxoExecucao | NewEtapaFluxoExecucao;
  }

  resetForm(form: EtapaFluxoExecucaoFormGroup, etapaFluxoExecucao: EtapaFluxoExecucaoFormGroupInput): void {
    const etapaFluxoExecucaoRawValue = { ...this.getFormDefaults(), ...etapaFluxoExecucao };
    form.reset(
      {
        ...etapaFluxoExecucaoRawValue,
        id: { value: etapaFluxoExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EtapaFluxoExecucaoFormDefaults {
    return {
      id: null,
      feito: false,
      agendada: false,
    };
  }
}
