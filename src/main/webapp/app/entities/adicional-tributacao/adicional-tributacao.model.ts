import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface IAdicionalTributacao {
  id: number;
  valor?: number | null;
  tributacao?: Pick<ITributacao, 'id' | 'nome'> | null;
  planoContabil?: Pick<IPlanoContabil, 'id' | 'nome'> | null;
}

export type NewAdicionalTributacao = Omit<IAdicionalTributacao, 'id'> & { id: null };
