import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IAgenteIntegracaoEstagio, NewAgenteIntegracaoEstagio } from '../agente-integracao-estagio.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAgenteIntegracaoEstagio for edit and NewAgenteIntegracaoEstagioFormGroupInput for create.
 */
type AgenteIntegracaoEstagioFormGroupInput = IAgenteIntegracaoEstagio | PartialWithRequiredKeyOf<NewAgenteIntegracaoEstagio>;

type AgenteIntegracaoEstagioFormDefaults = Pick<NewAgenteIntegracaoEstagio, 'id' | 'principal'>;

type AgenteIntegracaoEstagioFormGroupContent = {
  id: FormControl<IAgenteIntegracaoEstagio['id'] | NewAgenteIntegracaoEstagio['id']>;
  cnpj: FormControl<IAgenteIntegracaoEstagio['cnpj']>;
  razaoSocial: FormControl<IAgenteIntegracaoEstagio['razaoSocial']>;
  coordenador: FormControl<IAgenteIntegracaoEstagio['coordenador']>;
  cpfCoordenadorEstagio: FormControl<IAgenteIntegracaoEstagio['cpfCoordenadorEstagio']>;
  logradouro: FormControl<IAgenteIntegracaoEstagio['logradouro']>;
  numero: FormControl<IAgenteIntegracaoEstagio['numero']>;
  complemento: FormControl<IAgenteIntegracaoEstagio['complemento']>;
  bairro: FormControl<IAgenteIntegracaoEstagio['bairro']>;
  cep: FormControl<IAgenteIntegracaoEstagio['cep']>;
  principal: FormControl<IAgenteIntegracaoEstagio['principal']>;
  cidade: FormControl<IAgenteIntegracaoEstagio['cidade']>;
};

export type AgenteIntegracaoEstagioFormGroup = FormGroup<AgenteIntegracaoEstagioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AgenteIntegracaoEstagioFormService {
  createAgenteIntegracaoEstagioFormGroup(
    agenteIntegracaoEstagio: AgenteIntegracaoEstagioFormGroupInput = { id: null },
  ): AgenteIntegracaoEstagioFormGroup {
    const agenteIntegracaoEstagioRawValue = {
      ...this.getFormDefaults(),
      ...agenteIntegracaoEstagio,
    };
    return new FormGroup<AgenteIntegracaoEstagioFormGroupContent>({
      id: new FormControl(
        { value: agenteIntegracaoEstagioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      cnpj: new FormControl(agenteIntegracaoEstagioRawValue.cnpj),
      razaoSocial: new FormControl(agenteIntegracaoEstagioRawValue.razaoSocial, {
        validators: [Validators.maxLength(200)],
      }),
      coordenador: new FormControl(agenteIntegracaoEstagioRawValue.coordenador),
      cpfCoordenadorEstagio: new FormControl(agenteIntegracaoEstagioRawValue.cpfCoordenadorEstagio),
      logradouro: new FormControl(agenteIntegracaoEstagioRawValue.logradouro),
      numero: new FormControl(agenteIntegracaoEstagioRawValue.numero, {
        validators: [Validators.maxLength(5)],
      }),
      complemento: new FormControl(agenteIntegracaoEstagioRawValue.complemento, {
        validators: [Validators.maxLength(100)],
      }),
      bairro: new FormControl(agenteIntegracaoEstagioRawValue.bairro),
      cep: new FormControl(agenteIntegracaoEstagioRawValue.cep, {
        validators: [Validators.maxLength(9)],
      }),
      principal: new FormControl(agenteIntegracaoEstagioRawValue.principal),
      cidade: new FormControl(agenteIntegracaoEstagioRawValue.cidade, {
        validators: [Validators.required],
      }),
    });
  }

  getAgenteIntegracaoEstagio(form: AgenteIntegracaoEstagioFormGroup): IAgenteIntegracaoEstagio | NewAgenteIntegracaoEstagio {
    return form.getRawValue() as IAgenteIntegracaoEstagio | NewAgenteIntegracaoEstagio;
  }

  resetForm(form: AgenteIntegracaoEstagioFormGroup, agenteIntegracaoEstagio: AgenteIntegracaoEstagioFormGroupInput): void {
    const agenteIntegracaoEstagioRawValue = { ...this.getFormDefaults(), ...agenteIntegracaoEstagio };
    form.reset(
      {
        ...agenteIntegracaoEstagioRawValue,
        id: { value: agenteIntegracaoEstagioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AgenteIntegracaoEstagioFormDefaults {
    return {
      id: null,
      principal: false,
    };
  }
}
