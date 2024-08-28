import { IRamo } from 'app/entities/ramo/ramo.model';
import { IPlanoAssinaturaContabil } from 'app/entities/plano-assinatura-contabil/plano-assinatura-contabil.model';

export interface IAdicionalRamo {
  id: number;
  valor?: number | null;
  ramo?: IRamo | null;
  planoAssinaturaContabil?: IPlanoAssinaturaContabil | null;
}

export type NewAdicionalRamo = Omit<IAdicionalRamo, 'id'> & { id: null };
