import { IEsfera } from 'app/entities/esfera/esfera.model';

export interface IImposto {
  id: number;
  nome?: string | null;
  sigla?: string | null;
  descricao?: string | null;
  esfera?: IEsfera | null;
}

export type NewImposto = Omit<IImposto, 'id'> & { id: null };
