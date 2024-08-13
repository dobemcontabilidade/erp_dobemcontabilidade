import { ISecaoCnae } from 'app/entities/secao-cnae/secao-cnae.model';

export interface IDivisaoCnae {
  id: number;
  codigo?: string | null;
  descricao?: string | null;
  secao?: ISecaoCnae | null;
}

export type NewDivisaoCnae = Omit<IDivisaoCnae, 'id'> & { id: null };
