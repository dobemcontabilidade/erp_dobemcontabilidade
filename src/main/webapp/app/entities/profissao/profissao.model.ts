import { ISocio } from 'app/entities/socio/socio.model';

export interface IProfissao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  socio?: Pick<ISocio, 'id' | 'funcaoSocio'> | null;
}

export type NewProfissao = Omit<IProfissao, 'id'> & { id: null };
