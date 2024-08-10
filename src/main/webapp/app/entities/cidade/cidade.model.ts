import { IEstado } from 'app/entities/estado/estado.model';

export interface ICidade {
  id: number;
  nome?: string | null;
  contratacao?: boolean | null;
  abertura?: boolean | null;
  estado?: Pick<IEstado, 'id' | 'nome'> | null;
}

export type NewCidade = Omit<ICidade, 'id'> & { id: null };
