import { TipoRedeEnum } from 'app/entities/enumerations/tipo-rede-enum.model';

export interface IPerfilRedeSocial {
  id: number;
  rede?: string | null;
  urlPerfil?: string | null;
  tipoRede?: keyof typeof TipoRedeEnum | null;
}

export type NewPerfilRedeSocial = Omit<IPerfilRedeSocial, 'id'> & { id: null };
