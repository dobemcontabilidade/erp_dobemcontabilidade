import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IFluxoExecucao, NewFluxoExecucao } from '../fluxo-execucao.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFluxoExecucao for edit and NewFluxoExecucaoFormGroupInput for create.
 */
type FluxoExecucaoFormGroupInput = IFluxoExecucao | PartialWithRequiredKeyOf<NewFluxoExecucao>;

type FluxoExecucaoFormDefaults = Pick<NewFluxoExecucao, 'id'>;

type FluxoExecucaoFormGroupContent = {
  id: FormControl<IFluxoExecucao['id'] | NewFluxoExecucao['id']>;
  nome: FormControl<IFluxoExecucao['nome']>;
  descricao: FormControl<IFluxoExecucao['descricao']>;
};

export type FluxoExecucaoFormGroup = FormGroup<FluxoExecucaoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FluxoExecucaoFormService {
  createFluxoExecucaoFormGroup(fluxoExecucao: FluxoExecucaoFormGroupInput = { id: null }): FluxoExecucaoFormGroup {
    const fluxoExecucaoRawValue = {
      ...this.getFormDefaults(),
      ...fluxoExecucao,
    };
    return new FormGroup<FluxoExecucaoFormGroupContent>({
      id: new FormControl(
        { value: fluxoExecucaoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(fluxoExecucaoRawValue.nome),
      descricao: new FormControl(fluxoExecucaoRawValue.descricao),
    });
  }

  getFluxoExecucao(form: FluxoExecucaoFormGroup): IFluxoExecucao | NewFluxoExecucao {
    return form.getRawValue() as IFluxoExecucao | NewFluxoExecucao;
  }

  resetForm(form: FluxoExecucaoFormGroup, fluxoExecucao: FluxoExecucaoFormGroupInput): void {
    const fluxoExecucaoRawValue = { ...this.getFormDefaults(), ...fluxoExecucao };
    form.reset(
      {
        ...fluxoExecucaoRawValue,
        id: { value: fluxoExecucaoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FluxoExecucaoFormDefaults {
    return {
      id: null,
    };
  }
}
