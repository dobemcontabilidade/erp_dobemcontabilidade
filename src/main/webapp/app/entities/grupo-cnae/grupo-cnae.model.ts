import { IDivisaoCnae } from 'app/entities/divisao-cnae/divisao-cnae.model';

export interface IGrupoCnae {
  id: number;
  codigo?: string | null;
  descricao?: string | null;
  divisao?: Pick<IDivisaoCnae, 'id' | 'descricao'> | null;
}

export type NewGrupoCnae = Omit<IGrupoCnae, 'id'> & { id: null };
