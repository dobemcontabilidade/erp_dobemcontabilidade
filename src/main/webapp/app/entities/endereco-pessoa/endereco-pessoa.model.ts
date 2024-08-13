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
  residenciaPropria?: boolean | null;
  pessoa?: IPessoa | null;
  cidade?: ICidade | null;
}

export type NewEnderecoPessoa = Omit<IEnderecoPessoa, 'id'> & { id: null };
