import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IProfissao } from 'app/entities/profissao/profissao.model';
import { FuncaoSocioEnum } from 'app/entities/enumerations/funcao-socio-enum.model';

export interface ISocio {
  id: number;
  prolabore?: boolean | null;
  percentualSociedade?: number | null;
  adminstrador?: boolean | null;
  distribuicaoLucro?: boolean | null;
  responsavelReceita?: boolean | null;
  percentualDistribuicaoLucro?: number | null;
  funcaoSocio?: keyof typeof FuncaoSocioEnum | null;
  pessoa?: IPessoa | null;
  usuarioEmpresa?: IUsuarioEmpresa | null;
  empresa?: IEmpresa | null;
  profissao?: IProfissao | null;
}

export type NewSocio = Omit<ISocio, 'id'> & { id: null };
