import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { ICidade } from 'app/entities/cidade/cidade.model';

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
  empresa?: IEmpresa | null;
  cidade?: ICidade | null;
}

export type NewEnderecoEmpresa = Omit<IEnderecoEmpresa, 'id'> & { id: null };
