import dayjs from 'dayjs/esm';
import { IFuncionalidade } from 'app/entities/funcionalidade/funcionalidade.model';
import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';
import { IPermisao } from 'app/entities/permisao/permisao.model';

export interface IFuncionalidadeGrupoAcessoEmpresa {
  id: number;
  ativa?: string | null;
  dataExpiracao?: dayjs.Dayjs | null;
  ilimitado?: boolean | null;
  desabilitar?: boolean | null;
  funcionalidade?: IFuncionalidade | null;
  grupoAcessoEmpresa?: IGrupoAcessoEmpresa | null;
  permisao?: IPermisao | null;
}

export type NewFuncionalidadeGrupoAcessoEmpresa = Omit<IFuncionalidadeGrupoAcessoEmpresa, 'id'> & { id: null };
