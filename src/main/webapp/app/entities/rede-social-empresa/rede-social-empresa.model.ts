import { IRedeSocial } from 'app/entities/rede-social/rede-social.model';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';

export interface IRedeSocialEmpresa {
  id: number;
  perfil?: string | null;
  urlPerfil?: string | null;
  redeSocial?: IRedeSocial | null;
  pessoajuridica?: IPessoajuridica | null;
}

export type NewRedeSocialEmpresa = Omit<IRedeSocialEmpresa, 'id'> & { id: null };
