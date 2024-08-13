import { IImposto } from 'app/entities/imposto/imposto.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { SituacaoSolicitacaoParcelamentoEnum } from 'app/entities/enumerations/situacao-solicitacao-parcelamento-enum.model';

export interface IParcelamentoImposto {
  id: number;
  diaVencimento?: number | null;
  numeroParcelas?: number | null;
  urlArquivoNegociacao?: string | null;
  numeroParcelasPagas?: number | null;
  numeroParcelasRegatantes?: number | null;
  situacaoSolicitacaoParcelamentoEnum?: keyof typeof SituacaoSolicitacaoParcelamentoEnum | null;
  imposto?: IImposto | null;
  empresa?: IEmpresa | null;
}

export type NewParcelamentoImposto = Omit<IParcelamentoImposto, 'id'> & { id: null };
