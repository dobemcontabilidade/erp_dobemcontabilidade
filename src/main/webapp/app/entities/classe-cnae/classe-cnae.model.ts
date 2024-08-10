import { IGrupoCnae } from 'app/entities/grupo-cnae/grupo-cnae.model';

export interface IClasseCnae {
  id: number;
  codigo?: string | null;
  descricao?: string | null;
  grupo?: Pick<IGrupoCnae, 'id' | 'descricao'> | null;
}

export type NewClasseCnae = Omit<IClasseCnae, 'id'> & { id: null };
