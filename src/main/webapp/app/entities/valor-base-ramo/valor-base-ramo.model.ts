import { IRamo } from 'app/entities/ramo/ramo.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface IValorBaseRamo {
  id: number;
  valorBase?: number | null;
  ramo?: Pick<IRamo, 'id' | 'nome'> | null;
  planoContabil?: Pick<IPlanoContabil, 'id' | 'nome'> | null;
}

export type NewValorBaseRamo = Omit<IValorBaseRamo, 'id'> & { id: null };
