import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICalculoPlanoAssinatura, NewCalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICalculoPlanoAssinatura for edit and NewCalculoPlanoAssinaturaFormGroupInput for create.
 */
type CalculoPlanoAssinaturaFormGroupInput = ICalculoPlanoAssinatura | PartialWithRequiredKeyOf<NewCalculoPlanoAssinatura>;

type CalculoPlanoAssinaturaFormDefaults = Pick<NewCalculoPlanoAssinatura, 'id'>;

type CalculoPlanoAssinaturaFormGroupContent = {
  id: FormControl<ICalculoPlanoAssinatura['id'] | NewCalculoPlanoAssinatura['id']>;
  codigoAtendimento: FormControl<ICalculoPlanoAssinatura['codigoAtendimento']>;
  valorEnquadramento: FormControl<ICalculoPlanoAssinatura['valorEnquadramento']>;
  valorTributacao: FormControl<ICalculoPlanoAssinatura['valorTributacao']>;
  valorRamo: FormControl<ICalculoPlanoAssinatura['valorRamo']>;
  valorFuncionarios: FormControl<ICalculoPlanoAssinatura['valorFuncionarios']>;
  valorSocios: FormControl<ICalculoPlanoAssinatura['valorSocios']>;
  valorFaturamento: FormControl<ICalculoPlanoAssinatura['valorFaturamento']>;
  valorPlanoContabil: FormControl<ICalculoPlanoAssinatura['valorPlanoContabil']>;
  valorPlanoContabilComDesconto: FormControl<ICalculoPlanoAssinatura['valorPlanoContabilComDesconto']>;
  valorMensalidade: FormControl<ICalculoPlanoAssinatura['valorMensalidade']>;
  valorPeriodo: FormControl<ICalculoPlanoAssinatura['valorPeriodo']>;
  valorAno: FormControl<ICalculoPlanoAssinatura['valorAno']>;
  periodoPagamento: FormControl<ICalculoPlanoAssinatura['periodoPagamento']>;
  planoContabil: FormControl<ICalculoPlanoAssinatura['planoContabil']>;
  ramo: FormControl<ICalculoPlanoAssinatura['ramo']>;
  tributacao: FormControl<ICalculoPlanoAssinatura['tributacao']>;
  descontoPlanoContabil: FormControl<ICalculoPlanoAssinatura['descontoPlanoContabil']>;
  assinaturaEmpresa: FormControl<ICalculoPlanoAssinatura['assinaturaEmpresa']>;
  descontoPlanoContaAzul: FormControl<ICalculoPlanoAssinatura['descontoPlanoContaAzul']>;
  planoContaAzul: FormControl<ICalculoPlanoAssinatura['planoContaAzul']>;
};

export type CalculoPlanoAssinaturaFormGroup = FormGroup<CalculoPlanoAssinaturaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CalculoPlanoAssinaturaFormService {
  createCalculoPlanoAssinaturaFormGroup(
    calculoPlanoAssinatura: CalculoPlanoAssinaturaFormGroupInput = { id: null },
  ): CalculoPlanoAssinaturaFormGroup {
    const calculoPlanoAssinaturaRawValue = {
      ...this.getFormDefaults(),
      ...calculoPlanoAssinatura,
    };
    return new FormGroup<CalculoPlanoAssinaturaFormGroupContent>({
      id: new FormControl(
        { value: calculoPlanoAssinaturaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigoAtendimento: new FormControl(calculoPlanoAssinaturaRawValue.codigoAtendimento),
      valorEnquadramento: new FormControl(calculoPlanoAssinaturaRawValue.valorEnquadramento),
      valorTributacao: new FormControl(calculoPlanoAssinaturaRawValue.valorTributacao),
      valorRamo: new FormControl(calculoPlanoAssinaturaRawValue.valorRamo),
      valorFuncionarios: new FormControl(calculoPlanoAssinaturaRawValue.valorFuncionarios),
      valorSocios: new FormControl(calculoPlanoAssinaturaRawValue.valorSocios),
      valorFaturamento: new FormControl(calculoPlanoAssinaturaRawValue.valorFaturamento),
      valorPlanoContabil: new FormControl(calculoPlanoAssinaturaRawValue.valorPlanoContabil),
      valorPlanoContabilComDesconto: new FormControl(calculoPlanoAssinaturaRawValue.valorPlanoContabilComDesconto),
      valorMensalidade: new FormControl(calculoPlanoAssinaturaRawValue.valorMensalidade),
      valorPeriodo: new FormControl(calculoPlanoAssinaturaRawValue.valorPeriodo),
      valorAno: new FormControl(calculoPlanoAssinaturaRawValue.valorAno),
      periodoPagamento: new FormControl(calculoPlanoAssinaturaRawValue.periodoPagamento, {
        validators: [Validators.required],
      }),
      planoContabil: new FormControl(calculoPlanoAssinaturaRawValue.planoContabil, {
        validators: [Validators.required],
      }),
      ramo: new FormControl(calculoPlanoAssinaturaRawValue.ramo, {
        validators: [Validators.required],
      }),
      tributacao: new FormControl(calculoPlanoAssinaturaRawValue.tributacao, {
        validators: [Validators.required],
      }),
      descontoPlanoContabil: new FormControl(calculoPlanoAssinaturaRawValue.descontoPlanoContabil, {
        validators: [Validators.required],
      }),
      assinaturaEmpresa: new FormControl(calculoPlanoAssinaturaRawValue.assinaturaEmpresa, {
        validators: [Validators.required],
      }),
      descontoPlanoContaAzul: new FormControl(calculoPlanoAssinaturaRawValue.descontoPlanoContaAzul),
      planoContaAzul: new FormControl(calculoPlanoAssinaturaRawValue.planoContaAzul),
    });
  }

  getCalculoPlanoAssinatura(form: CalculoPlanoAssinaturaFormGroup): ICalculoPlanoAssinatura | NewCalculoPlanoAssinatura {
    return form.getRawValue() as ICalculoPlanoAssinatura | NewCalculoPlanoAssinatura;
  }

  resetForm(form: CalculoPlanoAssinaturaFormGroup, calculoPlanoAssinatura: CalculoPlanoAssinaturaFormGroupInput): void {
    const calculoPlanoAssinaturaRawValue = { ...this.getFormDefaults(), ...calculoPlanoAssinatura };
    form.reset(
      {
        ...calculoPlanoAssinaturaRawValue,
        id: { value: calculoPlanoAssinaturaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CalculoPlanoAssinaturaFormDefaults {
    return {
      id: null,
    };
  }
}
