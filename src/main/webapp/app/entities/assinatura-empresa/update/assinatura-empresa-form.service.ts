import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IAssinaturaEmpresa, NewAssinaturaEmpresa } from '../assinatura-empresa.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssinaturaEmpresa for edit and NewAssinaturaEmpresaFormGroupInput for create.
 */
type AssinaturaEmpresaFormGroupInput = IAssinaturaEmpresa | PartialWithRequiredKeyOf<NewAssinaturaEmpresa>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IAssinaturaEmpresa | NewAssinaturaEmpresa> = Omit<T, 'dataContratacao' | 'dataEncerramento'> & {
  dataContratacao?: string | null;
  dataEncerramento?: string | null;
};

type AssinaturaEmpresaFormRawValue = FormValueOf<IAssinaturaEmpresa>;

type NewAssinaturaEmpresaFormRawValue = FormValueOf<NewAssinaturaEmpresa>;

type AssinaturaEmpresaFormDefaults = Pick<NewAssinaturaEmpresa, 'id' | 'dataContratacao' | 'dataEncerramento'>;

type AssinaturaEmpresaFormGroupContent = {
  id: FormControl<AssinaturaEmpresaFormRawValue['id'] | NewAssinaturaEmpresa['id']>;
  codigoAssinatura: FormControl<AssinaturaEmpresaFormRawValue['codigoAssinatura']>;
  valorEnquadramento: FormControl<AssinaturaEmpresaFormRawValue['valorEnquadramento']>;
  valorTributacao: FormControl<AssinaturaEmpresaFormRawValue['valorTributacao']>;
  valorRamo: FormControl<AssinaturaEmpresaFormRawValue['valorRamo']>;
  valorFuncionarios: FormControl<AssinaturaEmpresaFormRawValue['valorFuncionarios']>;
  valorSocios: FormControl<AssinaturaEmpresaFormRawValue['valorSocios']>;
  valorFaturamento: FormControl<AssinaturaEmpresaFormRawValue['valorFaturamento']>;
  valorPlanoContabil: FormControl<AssinaturaEmpresaFormRawValue['valorPlanoContabil']>;
  valorPlanoContabilComDesconto: FormControl<AssinaturaEmpresaFormRawValue['valorPlanoContabilComDesconto']>;
  valorPlanoContaAzulComDesconto: FormControl<AssinaturaEmpresaFormRawValue['valorPlanoContaAzulComDesconto']>;
  valorMensalidade: FormControl<AssinaturaEmpresaFormRawValue['valorMensalidade']>;
  valorPeriodo: FormControl<AssinaturaEmpresaFormRawValue['valorPeriodo']>;
  valorAno: FormControl<AssinaturaEmpresaFormRawValue['valorAno']>;
  dataContratacao: FormControl<AssinaturaEmpresaFormRawValue['dataContratacao']>;
  dataEncerramento: FormControl<AssinaturaEmpresaFormRawValue['dataEncerramento']>;
  diaVencimento: FormControl<AssinaturaEmpresaFormRawValue['diaVencimento']>;
  situacaoContratoEmpresa: FormControl<AssinaturaEmpresaFormRawValue['situacaoContratoEmpresa']>;
  tipoContrato: FormControl<AssinaturaEmpresaFormRawValue['tipoContrato']>;
  periodoPagamento: FormControl<AssinaturaEmpresaFormRawValue['periodoPagamento']>;
  formaDePagamento: FormControl<AssinaturaEmpresaFormRawValue['formaDePagamento']>;
  planoContaAzul: FormControl<AssinaturaEmpresaFormRawValue['planoContaAzul']>;
  planoContabil: FormControl<AssinaturaEmpresaFormRawValue['planoContabil']>;
  empresa: FormControl<AssinaturaEmpresaFormRawValue['empresa']>;
};

export type AssinaturaEmpresaFormGroup = FormGroup<AssinaturaEmpresaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssinaturaEmpresaFormService {
  createAssinaturaEmpresaFormGroup(assinaturaEmpresa: AssinaturaEmpresaFormGroupInput = { id: null }): AssinaturaEmpresaFormGroup {
    const assinaturaEmpresaRawValue = this.convertAssinaturaEmpresaToAssinaturaEmpresaRawValue({
      ...this.getFormDefaults(),
      ...assinaturaEmpresa,
    });
    return new FormGroup<AssinaturaEmpresaFormGroupContent>({
      id: new FormControl(
        { value: assinaturaEmpresaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      codigoAssinatura: new FormControl(assinaturaEmpresaRawValue.codigoAssinatura),
      valorEnquadramento: new FormControl(assinaturaEmpresaRawValue.valorEnquadramento),
      valorTributacao: new FormControl(assinaturaEmpresaRawValue.valorTributacao),
      valorRamo: new FormControl(assinaturaEmpresaRawValue.valorRamo),
      valorFuncionarios: new FormControl(assinaturaEmpresaRawValue.valorFuncionarios),
      valorSocios: new FormControl(assinaturaEmpresaRawValue.valorSocios),
      valorFaturamento: new FormControl(assinaturaEmpresaRawValue.valorFaturamento),
      valorPlanoContabil: new FormControl(assinaturaEmpresaRawValue.valorPlanoContabil),
      valorPlanoContabilComDesconto: new FormControl(assinaturaEmpresaRawValue.valorPlanoContabilComDesconto),
      valorPlanoContaAzulComDesconto: new FormControl(assinaturaEmpresaRawValue.valorPlanoContaAzulComDesconto),
      valorMensalidade: new FormControl(assinaturaEmpresaRawValue.valorMensalidade),
      valorPeriodo: new FormControl(assinaturaEmpresaRawValue.valorPeriodo),
      valorAno: new FormControl(assinaturaEmpresaRawValue.valorAno),
      dataContratacao: new FormControl(assinaturaEmpresaRawValue.dataContratacao),
      dataEncerramento: new FormControl(assinaturaEmpresaRawValue.dataEncerramento),
      diaVencimento: new FormControl(assinaturaEmpresaRawValue.diaVencimento),
      situacaoContratoEmpresa: new FormControl(assinaturaEmpresaRawValue.situacaoContratoEmpresa),
      tipoContrato: new FormControl(assinaturaEmpresaRawValue.tipoContrato),
      periodoPagamento: new FormControl(assinaturaEmpresaRawValue.periodoPagamento, {
        validators: [Validators.required],
      }),
      formaDePagamento: new FormControl(assinaturaEmpresaRawValue.formaDePagamento, {
        validators: [Validators.required],
      }),
      planoContaAzul: new FormControl(assinaturaEmpresaRawValue.planoContaAzul, {
        validators: [Validators.required],
      }),
      planoContabil: new FormControl(assinaturaEmpresaRawValue.planoContabil, {
        validators: [Validators.required],
      }),
      empresa: new FormControl(assinaturaEmpresaRawValue.empresa, {
        validators: [Validators.required],
      }),
    });
  }

  getAssinaturaEmpresa(form: AssinaturaEmpresaFormGroup): IAssinaturaEmpresa | NewAssinaturaEmpresa {
    return this.convertAssinaturaEmpresaRawValueToAssinaturaEmpresa(
      form.getRawValue() as AssinaturaEmpresaFormRawValue | NewAssinaturaEmpresaFormRawValue,
    );
  }

  resetForm(form: AssinaturaEmpresaFormGroup, assinaturaEmpresa: AssinaturaEmpresaFormGroupInput): void {
    const assinaturaEmpresaRawValue = this.convertAssinaturaEmpresaToAssinaturaEmpresaRawValue({
      ...this.getFormDefaults(),
      ...assinaturaEmpresa,
    });
    form.reset(
      {
        ...assinaturaEmpresaRawValue,
        id: { value: assinaturaEmpresaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AssinaturaEmpresaFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      dataContratacao: currentTime,
      dataEncerramento: currentTime,
    };
  }

  private convertAssinaturaEmpresaRawValueToAssinaturaEmpresa(
    rawAssinaturaEmpresa: AssinaturaEmpresaFormRawValue | NewAssinaturaEmpresaFormRawValue,
  ): IAssinaturaEmpresa | NewAssinaturaEmpresa {
    return {
      ...rawAssinaturaEmpresa,
      dataContratacao: dayjs(rawAssinaturaEmpresa.dataContratacao, DATE_TIME_FORMAT),
      dataEncerramento: dayjs(rawAssinaturaEmpresa.dataEncerramento, DATE_TIME_FORMAT),
    };
  }

  private convertAssinaturaEmpresaToAssinaturaEmpresaRawValue(
    assinaturaEmpresa: IAssinaturaEmpresa | (Partial<NewAssinaturaEmpresa> & AssinaturaEmpresaFormDefaults),
  ): AssinaturaEmpresaFormRawValue | PartialWithRequiredKeyOf<NewAssinaturaEmpresaFormRawValue> {
    return {
      ...assinaturaEmpresa,
      dataContratacao: assinaturaEmpresa.dataContratacao ? assinaturaEmpresa.dataContratacao.format(DATE_TIME_FORMAT) : undefined,
      dataEncerramento: assinaturaEmpresa.dataEncerramento ? assinaturaEmpresa.dataEncerramento.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
