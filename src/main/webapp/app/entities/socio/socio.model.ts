import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
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
  pessoaFisica?: IPessoaFisica | null;
  empresa?: IEmpresa | null;
}

export type NewSocio = Omit<ISocio, 'id'> & { id: null };
