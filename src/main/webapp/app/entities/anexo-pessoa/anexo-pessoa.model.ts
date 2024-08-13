import { IPessoa } from 'app/entities/pessoa/pessoa.model';

export interface IAnexoPessoa {
  id: number;
  urlArquivo?: string | null;
  descricao?: string | null;
  pessoa?: IPessoa | null;
}

export type NewAnexoPessoa = Omit<IAnexoPessoa, 'id'> & { id: null };
