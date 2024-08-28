import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';

export interface IAdicionalTributacao {
  id: number;
  valor?: number | null;
  tributacao?: ITributacao | null;
  planoAssinaturaContabil?: IPlanoAssinaturaContabil | null;
}

export type NewAdicionalTributacao = Omit<IAdicionalTributacao, 'id'> & { id: null };
