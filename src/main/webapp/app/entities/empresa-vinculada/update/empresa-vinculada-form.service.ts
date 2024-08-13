import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEmpresaVinculada, NewEmpresaVinculada } from '../empresa-vinculada.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmpresaVinculada for edit and NewEmpresaVinculadaFormGroupInput for create.
 */
type EmpresaVinculadaFormGroupInput = IEmpresaVinculada | PartialWithRequiredKeyOf<NewEmpresaVinculada>;

type EmpresaVinculadaFormDefaults = Pick<NewEmpresaVinculada, 'id' | 'salarioFixo' | 'salarioVariavel'>;

type EmpresaVinculadaFormGroupContent = {
  id: FormControl<IEmpresaVinculada['id'] | NewEmpresaVinculada['id']>;
  nomeEmpresa: FormControl<IEmpresaVinculada['nomeEmpresa']>;
  cnpj: FormControl<IEmpresaVinculada['cnpj']>;
  remuneracaoEmpresa: FormControl<IEmpresaVinculada['remuneracaoEmpresa']>;
  observacoes: FormControl<IEmpresaVinculada['observacoes']>;
  salarioFixo: FormControl<IEmpresaVinculada['salarioFixo']>;
  salarioVariavel: FormControl<IEmpresaVinculada['salarioVariavel']>;
  valorSalarioFixo: FormControl<IEmpresaVinculada['valorSalarioFixo']>;
  dataTerminoContrato: FormControl<IEmpresaVinculada['dataTerminoContrato']>;
  numeroInscricao: FormControl<IEmpresaVinculada['numeroInscricao']>;
  codigoLotacao: FormControl<IEmpresaVinculada['codigoLotacao']>;
  descricaoComplementar: FormControl<IEmpresaVinculada['descricaoComplementar']>;
  descricaoCargo: FormControl<IEmpresaVinculada['descricaoCargo']>;
  observacaoJornadaTrabalho: FormControl<IEmpresaVinculada['observacaoJornadaTrabalho']>;
  mediaHorasTrabalhadasSemana: FormControl<IEmpresaVinculada['mediaHorasTrabalhadasSemana']>;
  regimePrevidenciario: FormControl<IEmpresaVinculada['regimePrevidenciario']>;
  unidadePagamentoSalario: FormControl<IEmpresaVinculada['unidadePagamentoSalario']>;
  jornadaEspecial: FormControl<IEmpresaVinculada['jornadaEspecial']>;
  tipoInscricaoEmpresaVinculada: FormControl<IEmpresaVinculada['tipoInscricaoEmpresaVinculada']>;
  tipoContratoTrabalho: FormControl<IEmpresaVinculada['tipoContratoTrabalho']>;
  tipoRegimeTrabalho: FormControl<IEmpresaVinculada['tipoRegimeTrabalho']>;
  diasDaSemana: FormControl<IEmpresaVinculada['diasDaSemana']>;
  tipoJornadaEmpresaVinculada: FormControl<IEmpresaVinculada['tipoJornadaEmpresaVinculada']>;
  funcionario: FormControl<IEmpresaVinculada['funcionario']>;
};

export type EmpresaVinculadaFormGroup = FormGroup<EmpresaVinculadaFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmpresaVinculadaFormService {
  createEmpresaVinculadaFormGroup(empresaVinculada: EmpresaVinculadaFormGroupInput = { id: null }): EmpresaVinculadaFormGroup {
    const empresaVinculadaRawValue = {
      ...this.getFormDefaults(),
      ...empresaVinculada,
    };
    return new FormGroup<EmpresaVinculadaFormGroupContent>({
      id: new FormControl(
        { value: empresaVinculadaRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomeEmpresa: new FormControl(empresaVinculadaRawValue.nomeEmpresa, {
        validators: [Validators.maxLength(200)],
      }),
      cnpj: new FormControl(empresaVinculadaRawValue.cnpj, {
        validators: [Validators.maxLength(14)],
      }),
      remuneracaoEmpresa: new FormControl(empresaVinculadaRawValue.remuneracaoEmpresa),
      observacoes: new FormControl(empresaVinculadaRawValue.observacoes, {
        validators: [Validators.maxLength(200)],
      }),
      salarioFixo: new FormControl(empresaVinculadaRawValue.salarioFixo),
      salarioVariavel: new FormControl(empresaVinculadaRawValue.salarioVariavel),
      valorSalarioFixo: new FormControl(empresaVinculadaRawValue.valorSalarioFixo),
      dataTerminoContrato: new FormControl(empresaVinculadaRawValue.dataTerminoContrato),
      numeroInscricao: new FormControl(empresaVinculadaRawValue.numeroInscricao),
      codigoLotacao: new FormControl(empresaVinculadaRawValue.codigoLotacao),
      descricaoComplementar: new FormControl(empresaVinculadaRawValue.descricaoComplementar),
      descricaoCargo: new FormControl(empresaVinculadaRawValue.descricaoCargo, {
        validators: [Validators.maxLength(200)],
      }),
      observacaoJornadaTrabalho: new FormControl(empresaVinculadaRawValue.observacaoJornadaTrabalho),
      mediaHorasTrabalhadasSemana: new FormControl(empresaVinculadaRawValue.mediaHorasTrabalhadasSemana),
      regimePrevidenciario: new FormControl(empresaVinculadaRawValue.regimePrevidenciario),
      unidadePagamentoSalario: new FormControl(empresaVinculadaRawValue.unidadePagamentoSalario),
      jornadaEspecial: new FormControl(empresaVinculadaRawValue.jornadaEspecial),
      tipoInscricaoEmpresaVinculada: new FormControl(empresaVinculadaRawValue.tipoInscricaoEmpresaVinculada),
      tipoContratoTrabalho: new FormControl(empresaVinculadaRawValue.tipoContratoTrabalho),
      tipoRegimeTrabalho: new FormControl(empresaVinculadaRawValue.tipoRegimeTrabalho),
      diasDaSemana: new FormControl(empresaVinculadaRawValue.diasDaSemana),
      tipoJornadaEmpresaVinculada: new FormControl(empresaVinculadaRawValue.tipoJornadaEmpresaVinculada),
      funcionario: new FormControl(empresaVinculadaRawValue.funcionario, {
        validators: [Validators.required],
      }),
    });
  }

  getEmpresaVinculada(form: EmpresaVinculadaFormGroup): IEmpresaVinculada | NewEmpresaVinculada {
    return form.getRawValue() as IEmpresaVinculada | NewEmpresaVinculada;
  }

  resetForm(form: EmpresaVinculadaFormGroup, empresaVinculada: EmpresaVinculadaFormGroupInput): void {
    const empresaVinculadaRawValue = { ...this.getFormDefaults(), ...empresaVinculada };
    form.reset(
      {
        ...empresaVinculadaRawValue,
        id: { value: empresaVinculadaRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmpresaVinculadaFormDefaults {
    return {
      id: null,
      salarioFixo: false,
      salarioVariavel: false,
    };
  }
}
