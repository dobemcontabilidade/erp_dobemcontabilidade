import { IRamo } from 'app/entities/ramo/ramo.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface IAdicionalRamo {
  id: number;
  valor?: number | null;
  ramo?: Pick<IRamo, 'id' | 'nome'> | null;
  planoContabil?: Pick<IPlanoContabil, 'id' | 'nome'> | null;
}

export type NewAdicionalRamo = Omit<IAdicionalRamo, 'id'> & { id: null };
