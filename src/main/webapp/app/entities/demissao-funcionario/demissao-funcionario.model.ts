import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { AvisoPrevioEnum } from 'app/entities/enumerations/aviso-previo-enum.model';
import { SituacaoDemissaoEnum } from 'app/entities/enumerations/situacao-demissao-enum.model';
import { TipoDemissaoEnum } from 'app/entities/enumerations/tipo-demissao-enum.model';

export interface IDemissaoFuncionario {
  id: number;
  numeroCertidaoObito?: string | null;
  cnpjEmpresaSucessora?: string | null;
  saldoFGTS?: string | null;
  valorPensao?: string | null;
  valorPensaoFgts?: string | null;
  percentualPensao?: string | null;
  percentualFgts?: string | null;
  diasAvisoPrevio?: number | null;
  dataAvisoPrevio?: string | null;
  dataPagamento?: string | null;
  dataAfastamento?: string | null;
  urlDemissional?: string | null;
  calcularRecisao?: boolean | null;
  pagar13Recisao?: boolean | null;
  jornadaTrabalhoCumpridaSemana?: boolean | null;
  sabadoCompesado?: boolean | null;
  novoVinculoComprovado?: boolean | null;
  dispensaAvisoPrevio?: boolean | null;
  fgtsArrecadadoGuia?: boolean | null;
  avisoPrevioTrabalhadoRecebido?: boolean | null;
  recolherFgtsMesAnterior?: boolean | null;
  avisoPrevioIndenizado?: boolean | null;
  cumprimentoAvisoPrevio?: number | null;
  avisoPrevio?: keyof typeof AvisoPrevioEnum | null;
  situacaoDemissao?: keyof typeof SituacaoDemissaoEnum | null;
  tipoDemissao?: keyof typeof TipoDemissaoEnum | null;
  funcionario?: IFuncionario | null;
}

export type NewDemissaoFuncionario = Omit<IDemissaoFuncionario, 'id'> & { id: null };
