import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IInstituicaoEnsino {
  id: number;
  nome?: string | null;
  cnpj?: string | null;
  logradouro?: string | null;
  numero?: string | null;
  complemento?: string | null;
  bairro?: string | null;
  cep?: string | null;
  principal?: boolean | null;
  cidade?: ICidade | null;
}

export type NewInstituicaoEnsino = Omit<IInstituicaoEnsino, 'id'> & { id: null };
