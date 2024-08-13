import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface IDescontoPlanoContabil {
  id: number;
  percentual?: number | null;
  periodoPagamento?: IPeriodoPagamento | null;
  planoContabil?: IPlanoContabil | null;
}

export type NewDescontoPlanoContabil = Omit<IDescontoPlanoContabil, 'id'> & { id: null };
