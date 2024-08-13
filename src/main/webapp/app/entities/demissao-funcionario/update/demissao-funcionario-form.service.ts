import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IDemissaoFuncionario, NewDemissaoFuncionario } from '../demissao-funcionario.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDemissaoFuncionario for edit and NewDemissaoFuncionarioFormGroupInput for create.
 */
type DemissaoFuncionarioFormGroupInput = IDemissaoFuncionario | PartialWithRequiredKeyOf<NewDemissaoFuncionario>;

type DemissaoFuncionarioFormDefaults = Pick<
  NewDemissaoFuncionario,
  | 'id'
  | 'calcularRecisao'
  | 'pagar13Recisao'
  | 'jornadaTrabalhoCumpridaSemana'
  | 'sabadoCompesado'
  | 'novoVinculoComprovado'
  | 'dispensaAvisoPrevio'
  | 'fgtsArrecadadoGuia'
  | 'avisoPrevioTrabalhadoRecebido'
  | 'recolherFgtsMesAnterior'
  | 'avisoPrevioIndenizado'
>;

type DemissaoFuncionarioFormGroupContent = {
  id: FormControl<IDemissaoFuncionario['id'] | NewDemissaoFuncionario['id']>;
  numeroCertidaoObito: FormControl<IDemissaoFuncionario['numeroCertidaoObito']>;
  cnpjEmpresaSucessora: FormControl<IDemissaoFuncionario['cnpjEmpresaSucessora']>;
  saldoFGTS: FormControl<IDemissaoFuncionario['saldoFGTS']>;
  valorPensao: FormControl<IDemissaoFuncionario['valorPensao']>;
  valorPensaoFgts: FormControl<IDemissaoFuncionario['valorPensaoFgts']>;
  percentualPensao: FormControl<IDemissaoFuncionario['percentualPensao']>;
  percentualFgts: FormControl<IDemissaoFuncionario['percentualFgts']>;
  diasAvisoPrevio: FormControl<IDemissaoFuncionario['diasAvisoPrevio']>;
  dataAvisoPrevio: FormControl<IDemissaoFuncionario['dataAvisoPrevio']>;
  dataPagamento: FormControl<IDemissaoFuncionario['dataPagamento']>;
  dataAfastamento: FormControl<IDemissaoFuncionario['dataAfastamento']>;
  urlDemissional: FormControl<IDemissaoFuncionario['urlDemissional']>;
  calcularRecisao: FormControl<IDemissaoFuncionario['calcularRecisao']>;
  pagar13Recisao: FormControl<IDemissaoFuncionario['pagar13Recisao']>;
  jornadaTrabalhoCumpridaSemana: FormControl<IDemissaoFuncionario['jornadaTrabalhoCumpridaSemana']>;
  sabadoCompesado: FormControl<IDemissaoFuncionario['sabadoCompesado']>;
  novoVinculoComprovado: FormControl<IDemissaoFuncionario['novoVinculoComprovado']>;
  dispensaAvisoPrevio: FormControl<IDemissaoFuncionario['dispensaAvisoPrevio']>;
  fgtsArrecadadoGuia: FormControl<IDemissaoFuncionario['fgtsArrecadadoGuia']>;
  avisoPrevioTrabalhadoRecebido: FormControl<IDemissaoFuncionario['avisoPrevioTrabalhadoRecebido']>;
  recolherFgtsMesAnterior: FormControl<IDemissaoFuncionario['recolherFgtsMesAnterior']>;
  avisoPrevioIndenizado: FormControl<IDemissaoFuncionario['avisoPrevioIndenizado']>;
  cumprimentoAvisoPrevio: FormControl<IDemissaoFuncionario['cumprimentoAvisoPrevio']>;
  avisoPrevio: FormControl<IDemissaoFuncionario['avisoPrevio']>;
  situacaoDemissao: FormControl<IDemissaoFuncionario['situacaoDemissao']>;
  tipoDemissao: FormControl<IDemissaoFuncionario['tipoDemissao']>;
  funcionario: FormControl<IDemissaoFuncionario['funcionario']>;
};

export type DemissaoFuncionarioFormGroup = FormGroup<DemissaoFuncionarioFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DemissaoFuncionarioFormService {
  createDemissaoFuncionarioFormGroup(demissaoFuncionario: DemissaoFuncionarioFormGroupInput = { id: null }): DemissaoFuncionarioFormGroup {
    const demissaoFuncionarioRawValue = {
      ...this.getFormDefaults(),
      ...demissaoFuncionario,
    };
    return new FormGroup<DemissaoFuncionarioFormGroupContent>({
      id: new FormControl(
        { value: demissaoFuncionarioRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      numeroCertidaoObito: new FormControl(demissaoFuncionarioRawValue.numeroCertidaoObito),
      cnpjEmpresaSucessora: new FormControl(demissaoFuncionarioRawValue.cnpjEmpresaSucessora),
      saldoFGTS: new FormControl(demissaoFuncionarioRawValue.saldoFGTS),
      valorPensao: new FormControl(demissaoFuncionarioRawValue.valorPensao),
      valorPensaoFgts: new FormControl(demissaoFuncionarioRawValue.valorPensaoFgts),
      percentualPensao: new FormControl(demissaoFuncionarioRawValue.percentualPensao),
      percentualFgts: new FormControl(demissaoFuncionarioRawValue.percentualFgts),
      diasAvisoPrevio: new FormControl(demissaoFuncionarioRawValue.diasAvisoPrevio),
      dataAvisoPrevio: new FormControl(demissaoFuncionarioRawValue.dataAvisoPrevio),
      dataPagamento: new FormControl(demissaoFuncionarioRawValue.dataPagamento),
      dataAfastamento: new FormControl(demissaoFuncionarioRawValue.dataAfastamento),
      urlDemissional: new FormControl(demissaoFuncionarioRawValue.urlDemissional),
      calcularRecisao: new FormControl(demissaoFuncionarioRawValue.calcularRecisao),
      pagar13Recisao: new FormControl(demissaoFuncionarioRawValue.pagar13Recisao),
      jornadaTrabalhoCumpridaSemana: new FormControl(demissaoFuncionarioRawValue.jornadaTrabalhoCumpridaSemana),
      sabadoCompesado: new FormControl(demissaoFuncionarioRawValue.sabadoCompesado),
      novoVinculoComprovado: new FormControl(demissaoFuncionarioRawValue.novoVinculoComprovado),
      dispensaAvisoPrevio: new FormControl(demissaoFuncionarioRawValue.dispensaAvisoPrevio),
      fgtsArrecadadoGuia: new FormControl(demissaoFuncionarioRawValue.fgtsArrecadadoGuia),
      avisoPrevioTrabalhadoRecebido: new FormControl(demissaoFuncionarioRawValue.avisoPrevioTrabalhadoRecebido),
      recolherFgtsMesAnterior: new FormControl(demissaoFuncionarioRawValue.recolherFgtsMesAnterior),
      avisoPrevioIndenizado: new FormControl(demissaoFuncionarioRawValue.avisoPrevioIndenizado),
      cumprimentoAvisoPrevio: new FormControl(demissaoFuncionarioRawValue.cumprimentoAvisoPrevio),
      avisoPrevio: new FormControl(demissaoFuncionarioRawValue.avisoPrevio),
      situacaoDemissao: new FormControl(demissaoFuncionarioRawValue.situacaoDemissao),
      tipoDemissao: new FormControl(demissaoFuncionarioRawValue.tipoDemissao),
      funcionario: new FormControl(demissaoFuncionarioRawValue.funcionario, {
        validators: [Validators.required],
      }),
    });
  }

  getDemissaoFuncionario(form: DemissaoFuncionarioFormGroup): IDemissaoFuncionario | NewDemissaoFuncionario {
    return form.getRawValue() as IDemissaoFuncionario | NewDemissaoFuncionario;
  }

  resetForm(form: DemissaoFuncionarioFormGroup, demissaoFuncionario: DemissaoFuncionarioFormGroupInput): void {
    const demissaoFuncionarioRawValue = { ...this.getFormDefaults(), ...demissaoFuncionario };
    form.reset(
      {
        ...demissaoFuncionarioRawValue,
        id: { value: demissaoFuncionarioRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): DemissaoFuncionarioFormDefaults {
    return {
      id: null,
      calcularRecisao: false,
      pagar13Recisao: false,
      jornadaTrabalhoCumpridaSemana: false,
      sabadoCompesado: false,
      novoVinculoComprovado: false,
      dispensaAvisoPrevio: false,
      fgtsArrecadadoGuia: false,
      avisoPrevioTrabalhadoRecebido: false,
      recolherFgtsMesAnterior: false,
      avisoPrevioIndenizado: false,
    };
  }
}
