import dayjs from 'dayjs/esm';
import { SituacaoUsuarioContadorEnum } from 'app/entities/enumerations/situacao-usuario-contador-enum.model';

export interface IUsuarioContador {
  id: number;
  email?: string | null;
  senha?: string | null;
  token?: string | null;
  ativo?: boolean | null;
  dataHoraAtivacao?: dayjs.Dayjs | null;
  dataLimiteAcesso?: dayjs.Dayjs | null;
  situacao?: keyof typeof SituacaoUsuarioContadorEnum | null;
}

export type NewUsuarioContador = Omit<IUsuarioContador, 'id'> & { id: null };
