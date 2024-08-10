import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface IAdicionalEnquadramento {
  id: number;
  valor?: number | null;
  enquadramento?: Pick<IEnquadramento, 'id' | 'nome'> | null;
  planoContabil?: Pick<IPlanoContabil, 'id' | 'nome'> | null;
}

export type NewAdicionalEnquadramento = Omit<IAdicionalEnquadramento, 'id'> & { id: null };
