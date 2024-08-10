import { IPessoa } from 'app/entities/pessoa/pessoa.model';

export interface IAnexoPessoa {
  id: number;
  urlArquivo?: string | null;
  tipo?: string | null;
  descricao?: string | null;
  pessoa?: Pick<IPessoa, 'id' | 'nome'> | null;
}

export type NewAnexoPessoa = Omit<IAnexoPessoa, 'id'> & { id: null };
