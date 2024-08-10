import { IPessoa } from 'app/entities/pessoa/pessoa.model';

export interface IAdministrador {
  id: number;
  nome?: string | null;
  sobreNome?: string | null;
  funcao?: string | null;
  pessoa?: Pick<IPessoa, 'id' | 'nome'> | null;
}

export type NewAdministrador = Omit<IAdministrador, 'id'> & { id: null };
