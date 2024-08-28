import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';

export interface IDescontoPeriodoPagamento {
  id: number;
  percentual?: number | null;
  periodoPagamento?: IPeriodoPagamento | null;
  planoAssinaturaContabil?: IPlanoAssinaturaContabil | null;
}

export type NewDescontoPeriodoPagamento = Omit<IDescontoPeriodoPagamento, 'id'> & { id: null };
