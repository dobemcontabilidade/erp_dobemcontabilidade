import { IEtapaFluxoModelo } from 'app/entities/etapa-fluxo-modelo/etapa-fluxo-modelo.model';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';

export interface IServicoContabilEtapaFluxoModelo {
  id: number;
  ordem?: number | null;
  prazo?: number | null;
  etapaFluxoModelo?: IEtapaFluxoModelo | null;
  servicoContabil?: IServicoContabil | null;
}

export type NewServicoContabilEtapaFluxoModelo = Omit<IServicoContabilEtapaFluxoModelo, 'id'> & { id: null };
