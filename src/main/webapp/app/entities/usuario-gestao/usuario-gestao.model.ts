import dayjs from 'dayjs/esm';
import { IAdministrador } from 'app/entities/administrador/administrador.model';
import { SituacaoUsuarioGestaoEnum } from 'app/entities/enumerations/situacao-usuario-gestao-enum.model';

export interface IUsuarioGestao {
  id: number;
  email?: string | null;
  senha?: string | null;
  token?: string | null;
  ativo?: boolean | null;
  dataHoraAtivacao?: dayjs.Dayjs | null;
  dataLimiteAcesso?: dayjs.Dayjs | null;
  situacaoUsuarioGestao?: keyof typeof SituacaoUsuarioGestaoEnum | null;
  administrador?: IAdministrador | null;
}

export type NewUsuarioGestao = Omit<IUsuarioGestao, 'id'> & { id: null };
