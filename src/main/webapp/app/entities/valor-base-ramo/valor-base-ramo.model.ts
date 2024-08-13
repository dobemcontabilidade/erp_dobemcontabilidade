import { IRamo } from 'app/entities/ramo/ramo.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface IValorBaseRamo {
  id: number;
  valorBase?: number | null;
  ramo?: IRamo | null;
  planoContabil?: IPlanoContabil | null;
}

export type NewValorBaseRamo = Omit<IValorBaseRamo, 'id'> & { id: null };
