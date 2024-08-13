import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IGatewayAssinaturaEmpresa, NewGatewayAssinaturaEmpresa } from '../gateway-assinatura-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IGatewayAssinaturaEmpresa for edit and NewGatewayAssinaturaEmpresaFormGroupInput for create.
 */
type GatewayAssinaturaEmpresaFormGroupInput = IGatewayAssinaturaEmpresa | PartialWithRequiredKeyOf<NewGatewayAssinaturaEmpresa>;

type GatewayAssinaturaEmpresaFormDefaults = Pick<NewGatewayAssinaturaEmpresa, 'id' | 'ativo'>;

type GatewayAssinaturaEmpresaFormGroupContent = {
  id: FormControl<IGatewayAssinaturaEmpresa['id'] | NewGatewayAssinaturaEmpresa['id']>;
  ativo: FormControl<IGatewayAssinaturaEmpresa['ativo']>;
  codigoAssinatura: FormControl<IGatewayAssinaturaEmpresa['codigoAssinatura']>;
  assinaturaEmpresa: FormControl<IGatewayAssinaturaEmpresa['assinaturaEmpresa']>;
  gatewayPagamento: FormControl<IGatewayAssinaturaEmpresa['gatewayPagamento']>;
};

export type GatewayAssinaturaEmpresaFormGroup = FormGroup<GatewayAssinaturaEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class GatewayAssinaturaEmpresaFormService {
  createGatewayAssinaturaEmpresaFormGroup(
    gatewayAssinaturaEmpresa: GatewayAssinaturaEmpresaFormGroupInput = { id: null },
  ): GatewayAssinaturaEmpresaFormGroup {
    const gatewayAssinaturaEmpresaRawValue = {
      ...this.getFormDefaults(),
      ...gatewayAssinaturaEmpresa,
    };
    return new FormGroup<GatewayAssinaturaEmpresaFormGroupContent>({
      id: new FormControl(
        { value: gatewayAssinaturaEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      ativo: new FormControl(gatewayAssinaturaEmpresaRawValue.ativo),
      codigoAssinatura: new FormControl(gatewayAssinaturaEmpresaRawValue.codigoAssinatura),
      assinaturaEmpresa: new FormControl(gatewayAssinaturaEmpresaRawValue.assinaturaEmpresa, {
        validators: [Validators.required],
      }),
      gatewayPagamento: new FormControl(gatewayAssinaturaEmpresaRawValue.gatewayPagamento, {
        validators: [Validators.required],
      }),
    });
  }

  getGatewayAssinaturaEmpresa(form: GatewayAssinaturaEmpresaFormGroup): IGatewayAssinaturaEmpresa | NewGatewayAssinaturaEmpresa {
    return form.getRawValue() as IGatewayAssinaturaEmpresa | NewGatewayAssinaturaEmpresa;
  }

  resetForm(form: GatewayAssinaturaEmpresaFormGroup, gatewayAssinaturaEmpresa: GatewayAssinaturaEmpresaFormGroupInput): void {
    const gatewayAssinaturaEmpresaRawValue = { ...this.getFormDefaults(), ...gatewayAssinaturaEmpresa };
    form.reset(
      {
        ...gatewayAssinaturaEmpresaRawValue,
        id: { value: gatewayAssinaturaEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): GatewayAssinaturaEmpresaFormDefaults {
    return {
      id: null,
      ativo: false,
    };
  }
}
