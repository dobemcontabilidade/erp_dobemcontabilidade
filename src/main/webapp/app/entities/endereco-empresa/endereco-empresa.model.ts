import { ICidade } from 'app/entities/cidade/cidade.model';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';

export interface IEnderecoEmpresa {
  id: number;
  logradouro?: string | null;
  numero?: string | null;
  complemento?: string | null;
  bairro?: string | null;
  cep?: string | null;
  principal?: boolean | null;
  filial?: boolean | null;
  enderecoFiscal?: boolean | null;
  cidade?: ICidade | null;
  pessoaJuridica?: IPessoajuridica | null;
}

export type NewEnderecoEmpresa = Omit<IEnderecoEmpresa, 'id'> & { id: null };
