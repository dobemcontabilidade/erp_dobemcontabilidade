import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IAgenteIntegracaoEstagio {
  id: number;
  cnpj?: string | null;
  razaoSocial?: string | null;
  coordenador?: string | null;
  cpfCoordenadorEstagio?: string | null;
  logradouro?: string | null;
  numero?: string | null;
  complemento?: string | null;
  bairro?: string | null;
  cep?: string | null;
  principal?: boolean | null;
  cidade?: ICidade | null;
}

export type NewAgenteIntegracaoEstagio = Omit<IAgenteIntegracaoEstagio, 'id'> & { id: null };
