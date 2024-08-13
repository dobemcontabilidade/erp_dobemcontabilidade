import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IContratoFuncionario, NewContratoFuncionario } from '../contrato-funcionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IContratoFuncionario for edit and NewContratoFuncionarioFormGroupInput for create.
 */
type ContratoFuncionarioFormGroupInput = IContratoFuncionario | PartialWithRequiredKeyOf<NewContratoFuncionario>;

type ContratoFuncionarioFormDefaults = Pick<NewContratoFuncionario, 'id' | 'salarioFixo' | 'salarioVariavel' | 'estagio'>;

type ContratoFuncionarioFormGroupContent = {
  id: FormControl<IContratoFuncionario['id'] | NewContratoFuncionario['id']>;
  salarioFixo: FormControl<IContratoFuncionario['salarioFixo']>;
  salarioVariavel: FormControl<IContratoFuncionario['salarioVariavel']>;
  estagio: FormControl<IContratoFuncionario['estagio']>;
  naturezaEstagioEnum: FormControl<IContratoFuncionario['naturezaEstagioEnum']>;
  ctps: FormControl<IContratoFuncionario['ctps']>;
  serieCtps: FormControl<IContratoFuncionario['serieCtps']>;
  orgaoEmissorDocumento: FormControl<IContratoFuncionario['orgaoEmissorDocumento']>;
  dataValidadeDocumento: FormControl<IContratoFuncionario['dataValidadeDocumento']>;
  dataAdmissao: FormControl<IContratoFuncionario['dataAdmissao']>;
  cargo: FormControl<IContratoFuncionario['cargo']>;
  descricaoAtividades: FormControl<IContratoFuncionario['descricaoAtividades']>;
  situacao: FormControl<IContratoFuncionario['situacao']>;
  valorSalarioFixo: FormControl<IContratoFuncionario['valorSalarioFixo']>;
  valorSalarioVariavel: FormControl<IContratoFuncionario['valorSalarioVariavel']>;
  dataTerminoContrato: FormControl<IContratoFuncionario['dataTerminoContrato']>;
  datainicioContrato: FormControl<IContratoFuncionario['datainicioContrato']>;
  horasATrabalhadar: FormControl<IContratoFuncionario['horasATrabalhadar']>;
  codigoCargo: FormControl<IContratoFuncionario['codigoCargo']>;
  categoriaTrabalhador: FormControl<IContratoFuncionario['categoriaTrabalhador']>;
  tipoVinculoTrabalho: FormControl<IContratoFuncionario['tipoVinculoTrabalho']>;
  fgtsOpcao: FormControl<IContratoFuncionario['fgtsOpcao']>;
  tIpoDocumentoEnum: FormControl<IContratoFuncionario['tIpoDocumentoEnum']>;
  periodoExperiencia: FormControl<IContratoFuncionario['periodoExperiencia']>;
  tipoAdmisaoEnum: FormControl<IContratoFuncionario['tipoAdmisaoEnum']>;
  periodoIntermitente: FormControl<IContratoFuncionario['periodoIntermitente']>;
  indicativoAdmissao: FormControl<IContratoFuncionario['indicativoAdmissao']>;
  numeroPisNisPasep: FormControl<IContratoFuncionario['numeroPisNisPasep']>;
  funcionario: FormControl<IContratoFuncionario['funcionario']>;
  agenteIntegracaoEstagio: FormControl<IContratoFuncionario['agenteIntegracaoEstagio']>;
  instituicaoEnsino: FormControl<IContratoFuncionario['instituicaoEnsino']>;
};

export type ContratoFuncionarioFormGroup = FormGroup<ContratoFuncionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ContratoFuncionarioFormService {
  createContratoFuncionarioFormGroup(contratoFuncionario: ContratoFuncionarioFormGroupInput = { id: null }): ContratoFuncionarioFormGroup {
    const contratoFuncionarioRawValue = {
      ...this.getFormDefaults(),
      ...contratoFuncionario,
    };
    return new FormGroup<ContratoFuncionarioFormGroupContent>({
      id: new FormControl(
        { value: contratoFuncionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      salarioFixo: new FormControl(contratoFuncionarioRawValue.salarioFixo),
      salarioVariavel: new FormControl(contratoFuncionarioRawValue.salarioVariavel),
      estagio: new FormControl(contratoFuncionarioRawValue.estagio),
      naturezaEstagioEnum: new FormControl(contratoFuncionarioRawValue.naturezaEstagioEnum),
      ctps: new FormControl(contratoFuncionarioRawValue.ctps),
      serieCtps: new FormControl(contratoFuncionarioRawValue.serieCtps),
      orgaoEmissorDocumento: new FormControl(contratoFuncionarioRawValue.orgaoEmissorDocumento),
      dataValidadeDocumento: new FormControl(contratoFuncionarioRawValue.dataValidadeDocumento),
      dataAdmissao: new FormControl(contratoFuncionarioRawValue.dataAdmissao),
      cargo: new FormControl(contratoFuncionarioRawValue.cargo),
      descricaoAtividades: new FormControl(contratoFuncionarioRawValue.descricaoAtividades),
      situacao: new FormControl(contratoFuncionarioRawValue.situacao),
      valorSalarioFixo: new FormControl(contratoFuncionarioRawValue.valorSalarioFixo),
      valorSalarioVariavel: new FormControl(contratoFuncionarioRawValue.valorSalarioVariavel),
      dataTerminoContrato: new FormControl(contratoFuncionarioRawValue.dataTerminoContrato),
      datainicioContrato: new FormControl(contratoFuncionarioRawValue.datainicioContrato),
      horasATrabalhadar: new FormControl(contratoFuncionarioRawValue.horasATrabalhadar),
      codigoCargo: new FormControl(contratoFuncionarioRawValue.codigoCargo, {
        validators: [Validators.maxLength(10)],
      }),
      categoriaTrabalhador: new FormControl(contratoFuncionarioRawValue.categoriaTrabalhador),
      tipoVinculoTrabalho: new FormControl(contratoFuncionarioRawValue.tipoVinculoTrabalho),
      fgtsOpcao: new FormControl(contratoFuncionarioRawValue.fgtsOpcao),
      tIpoDocumentoEnum: new FormControl(contratoFuncionarioRawValue.tIpoDocumentoEnum),
      periodoExperiencia: new FormControl(contratoFuncionarioRawValue.periodoExperiencia),
      tipoAdmisaoEnum: new FormControl(contratoFuncionarioRawValue.tipoAdmisaoEnum),
      periodoIntermitente: new FormControl(contratoFuncionarioRawValue.periodoIntermitente),
      indicativoAdmissao: new FormControl(contratoFuncionarioRawValue.indicativoAdmissao),
      numeroPisNisPasep: new FormControl(contratoFuncionarioRawValue.numeroPisNisPasep),
      funcionario: new FormControl(contratoFuncionarioRawValue.funcionario, {
        validators: [Validators.required],
      }),
      agenteIntegracaoEstagio: new FormControl(contratoFuncionarioRawValue.agenteIntegracaoEstagio),
      instituicaoEnsino: new FormControl(contratoFuncionarioRawValue.instituicaoEnsino),
    });
  }

  getContratoFuncionario(form: ContratoFuncionarioFormGroup): IContratoFuncionario | NewContratoFuncionario {
    return form.getRawValue() as IContratoFuncionario | NewContratoFuncionario;
  }

  resetForm(form: ContratoFuncionarioFormGroup, contratoFuncionario: ContratoFuncionarioFormGroupInput): void {
    const contratoFuncionarioRawValue = { ...this.getFormDefaults(), ...contratoFuncionario };
    form.reset(
      {
        ...contratoFuncionarioRawValue,
        id: { value: contratoFuncionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ContratoFuncionarioFormDefaults {
    return {
      id: null,
      salarioFixo: false,
      salarioVariavel: false,
      estagio: false,
    };
  }
}
