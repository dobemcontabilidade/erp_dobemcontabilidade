import dayjs from 'dayjs/esm';
import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { IPermisao } from 'app/entities/permisao/permisao.model';
import { IGrupoAcessoEmpresa } from 'app/entities/grupo-acesso-empresa/grupo-acesso-empresa.model';

export interface IGrupoAcessoEmpresaUsuarioContador {
  id: number;
  nome?: string | null;
  dataExpiracao?: dayjs.Dayjs | null;
  ilimitado?: boolean | null;
  desabilitar?: boolean | null;
  usuarioContador?: IUsuarioContador | null;
  permisao?: IPermisao | null;
  grupoAcessoEmpresa?: IGrupoAcessoEmpresa | null;
}

export type NewGrupoAcessoEmpresaUsuarioContador = Omit<IGrupoAcessoEmpresaUsuarioContador, 'id'> & { id: null };
