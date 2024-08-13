import { IPessoa } from 'app/entities/pessoa/pessoa.model';

export interface IAdministrador {
  id: number;
  nome?: string | null;
  sobrenome?: string | null;
  funcao?: string | null;
  pessoa?: IPessoa | null;
}

export type NewAdministrador = Omit<IAdministrador, 'id'> & { id: null };
