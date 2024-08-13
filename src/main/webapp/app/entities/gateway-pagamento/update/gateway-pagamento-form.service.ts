import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGatewayPagamento, NewGatewayPagamento } from '../gateway-pagamento.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGatewayPagamento for edit and NewGatewayPagamentoFormGroupInput for create.
 */
type GatewayPagamentoFormGroupInput = IGatewayPagamento | PartialWithRequiredKeyOf<NewGatewayPagamento>;

type GatewayPagamentoFormDefaults = Pick<NewGatewayPagamento, 'id'>;

type GatewayPagamentoFormGroupContent = {
  id: FormControl<IGatewayPagamento['id'] | NewGatewayPagamento['id']>;
  nome: FormControl<IGatewayPagamento['nome']>;
  descricao: FormControl<IGatewayPagamento['descricao']>;
};

export type GatewayPagamentoFormGroup = FormGroup<GatewayPagamentoFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GatewayPagamentoFormService {
  createGatewayPagamentoFormGroup(gatewayPagamento: GatewayPagamentoFormGroupInput = { id: null }): GatewayPagamentoFormGroup {
    const gatewayPagamentoRawValue = {
      ...this.getFormDefaults(),
      ...gatewayPagamento,
    };
    return new FormGroup<GatewayPagamentoFormGroupContent>({
      id: new FormControl(
        { value: gatewayPagamentoRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nome: new FormControl(gatewayPagamentoRawValue.nome),
      descricao: new FormControl(gatewayPagamentoRawValue.descricao, {
        validators: [Validators.maxLength(200)],
      }),
    });
  }

  getGatewayPagamento(form: GatewayPagamentoFormGroup): IGatewayPagamento | NewGatewayPagamento {
    return form.getRawValue() as IGatewayPagamento | NewGatewayPagamento;
  }

  resetForm(form: GatewayPagamentoFormGroup, gatewayPagamento: GatewayPagamentoFormGroupInput): void {
    const gatewayPagamentoRawValue = { ...this.getFormDefaults(), ...gatewayPagamento };
    form.reset(
      {
        ...gatewayPagamentoRawValue,
        id: { value: gatewayPagamentoRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GatewayPagamentoFormDefaults {
    return {
      id: null,
    };
  }
}
