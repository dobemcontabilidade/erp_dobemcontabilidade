import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';

export interface IAdicionalEnquadramento {
  id: number;
  valor?: number | null;
  enquadramento?: IEnquadramento | null;
  planoAssinaturaContabil?: IPlanoAssinaturaContabil | null;
}

export type NewAdicionalEnquadramento = Omit<IAdicionalEnquadramento, 'id'> & { id: null };
