import dayjs from 'dayjs/esm';
import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { IGrupoAcessoPadrao } from 'app/entities/grupo-acesso-padrao/grupo-acesso-padrao.model';

export interface IGrupoAcessoUsuarioContador {
  id: number;
  dataExpiracao?: dayjs.Dayjs | null;
  ilimitado?: boolean | null;
  desabilitar?: boolean | null;
  usuarioContador?: IUsuarioContador | null;
  grupoAcessoPadrao?: IGrupoAcessoPadrao | null;
}

export type NewGrupoAcessoUsuarioContador = Omit<IGrupoAcessoUsuarioContador, 'id'> & { id: null };
