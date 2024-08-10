import dayjs from 'dayjs/esm';
import { IContador } from 'app/entities/contador/contador.model';
import { IAdministrador } from 'app/entities/administrador/administrador.model';
import { SituacaoUsuarioContadorEnum } from 'app/entities/enumerations/situacao-usuario-contador-enum.model';

export interface IUsuarioContador {
  id: number;
  email?: string | null;
  senha?: string | null;
  token?: string | null;
  dataHoraAtivacao?: dayjs.Dayjs | null;
  dataLimiteAcesso?: dayjs.Dayjs | null;
  situacao?: keyof typeof SituacaoUsuarioContadorEnum | null;
  contador?: Pick<IContador, 'id' | 'nome'> | null;
  administrador?: Pick<IAdministrador, 'id' | 'funcao'> | null;
}

export type NewUsuarioContador = Omit<IUsuarioContador, 'id'> & { id: null };
