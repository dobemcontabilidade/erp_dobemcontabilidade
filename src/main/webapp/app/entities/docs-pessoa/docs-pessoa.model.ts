import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';

export interface IDocsPessoa {
  id: number;
  urlArquivo?: string | null;
  tipo?: string | null;
  descricao?: string | null;
  pessoa?: IPessoaFisica | null;
}

export type NewDocsPessoa = Omit<IDocsPessoa, 'id'> & { id: null };
