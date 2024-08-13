import { ISistema } from 'app/entities/sistema/sistema.model';

export interface IModulo {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  sistema?: ISistema | null;
}

export type NewModulo = Omit<IModulo, 'id'> & { id: null };
