import dayjs from 'dayjs/esm';
import { IFuncionalidade } from 'app/entities/funcionalidade/funcionalidade.model';
import { IGrupoAcessoPadrao } from 'app/entities/grupo-acesso-padrao/grupo-acesso-padrao.model';
import { IPermisao } from 'app/entities/permisao/permisao.model';

export interface IFuncionalidadeGrupoAcessoPadrao {
  id: number;
  autorizado?: boolean | null;
  dataExpiracao?: dayjs.Dayjs | null;
  dataAtribuicao?: dayjs.Dayjs | null;
  funcionalidade?: IFuncionalidade | null;
  grupoAcessoPadrao?: IGrupoAcessoPadrao | null;
  permisao?: IPermisao | null;
}

export type NewFuncionalidadeGrupoAcessoPadrao = Omit<IFuncionalidadeGrupoAcessoPadrao, 'id'> & { id: null };
