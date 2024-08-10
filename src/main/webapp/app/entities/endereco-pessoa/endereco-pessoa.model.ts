import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IEnderecoPessoa {
  id: number;
  logradouro?: string | null;
  numero?: string | null;
  complemento?: string | null;
  bairro?: string | null;
  cep?: string | null;
  principal?: boolean | null;
  pessoa?: Pick<IPessoa, 'id' | 'nome'> | null;
  cidade?: Pick<ICidade, 'id' | 'nome'> | null;
}

export type NewEnderecoPessoa = Omit<IEnderecoPessoa, 'id'> & { id: null };
