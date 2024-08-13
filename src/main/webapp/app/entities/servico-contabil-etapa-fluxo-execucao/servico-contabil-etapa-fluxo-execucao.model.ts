import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { IEtapaFluxoExecucao } from 'app/entities/etapa-fluxo-execucao/etapa-fluxo-execucao.model';

export interface IServicoContabilEtapaFluxoExecucao {
  id: number;
  ordem?: number | null;
  feito?: boolean | null;
  prazo?: number | null;
  servicoContabil?: IServicoContabil | null;
  etapaFluxoExecucao?: IEtapaFluxoExecucao | null;
}

export type NewServicoContabilEtapaFluxoExecucao = Omit<IServicoContabilEtapaFluxoExecucao, 'id'> & { id: null };
