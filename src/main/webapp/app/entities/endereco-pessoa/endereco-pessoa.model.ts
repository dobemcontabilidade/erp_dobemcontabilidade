import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IEnderecoPessoa {
  id: number;
  logradouro?: string | null;
  numero?: string | null;
  complemento?: string | null;
  bairro?: string | null;
  cep?: string | null;
  principal?: boolean | null;
  pessoa?: IPessoaFisica | null;
  cidade?: ICidade | null;
}

export type NewEnderecoPessoa = Omit<IEnderecoPessoa, 'id'> & { id: null };
