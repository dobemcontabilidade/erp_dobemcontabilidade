import { IPlanoContaAzul } from 'app/entities/plano-conta-azul/plano-conta-azul.model';
import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';

export interface IDescontoPlanoContaAzul {
  id: number;
  percentual?: number | null;
  planoContaAzul?: IPlanoContaAzul | null;
  periodoPagamento?: IPeriodoPagamento | null;
}

export type NewDescontoPlanoContaAzul = Omit<IDescontoPlanoContaAzul, 'id'> & { id: null };
