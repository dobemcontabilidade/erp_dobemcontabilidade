import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { FuncaoSocioEnum } from 'app/entities/enumerations/funcao-socio-enum.model';

export interface ISocio {
  id: number;
  nome?: string | null;
  prolabore?: boolean | null;
  percentualSociedade?: number | null;
  adminstrador?: boolean | null;
  distribuicaoLucro?: boolean | null;
  responsavelReceita?: boolean | null;
  percentualDistribuicaoLucro?: number | null;
  funcaoSocio?: keyof typeof FuncaoSocioEnum | null;
  pessoa?: Pick<IPessoa, 'id' | 'nome'> | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
}

export type NewSocio = Omit<ISocio, 'id'> & { id: null };
