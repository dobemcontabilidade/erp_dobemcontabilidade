import dayjs from 'dayjs/esm';
import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';
import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';

export interface IGrupoAcessoUsuarioEmpresa {
  id: number;
  nome?: string | null;
  dataExpiracao?: dayjs.Dayjs | null;
  ilimitado?: boolean | null;
  desabilitar?: boolean | null;
  grupoAcessoEmpresa?: IGrupoAcessoEmpresa | null;
  usuarioEmpresa?: IUsuarioEmpresa | null;
}

export type NewGrupoAcessoUsuarioEmpresa = Omit<IGrupoAcessoUsuarioEmpresa, 'id'> & { id: null };
