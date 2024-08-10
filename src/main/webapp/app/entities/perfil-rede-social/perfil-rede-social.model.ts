import { TipoRedeSocialEnum } from 'app/entities/enumerations/tipo-rede-social-enum.model';

export interface IPerfilRedeSocial {
  id: number;
  rede?: string | null;
  urlPerfil?: string | null;
  tipoRede?: keyof typeof TipoRedeSocialEnum | null;
}

export type NewPerfilRedeSocial = Omit<IPerfilRedeSocial, 'id'> & { id: null };
