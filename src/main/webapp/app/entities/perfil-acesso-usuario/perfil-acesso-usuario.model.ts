import dayjs from 'dayjs/esm';

export interface IPerfilAcessoUsuario {
  id: number;
  nome?: string | null;
  autorizado?: boolean | null;
  dataExpiracao?: dayjs.Dayjs | null;
}

export type NewPerfilAcessoUsuario = Omit<IPerfilAcessoUsuario, 'id'> & { id: null };
