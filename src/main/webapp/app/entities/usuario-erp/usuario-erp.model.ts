import dayjs from 'dayjs/esm';
import { IAdministrador } from 'app/entities/administrador/administrador.model';
import { SituacaoUsuarioErpEnum } from 'app/entities/enumerations/situacao-usuario-erp-enum.model';

export interface IUsuarioErp {
  id: number;
  email?: string | null;
  senha?: string | null;
  dataHoraAtivacao?: dayjs.Dayjs | null;
  dataLimiteAcesso?: dayjs.Dayjs | null;
  situacao?: keyof typeof SituacaoUsuarioErpEnum | null;
  administrador?: Pick<IAdministrador, 'id' | 'nome'> | null;
}

export type NewUsuarioErp = Omit<IUsuarioErp, 'id'> & { id: null };
