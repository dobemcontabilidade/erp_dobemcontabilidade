export interface IRedeSocial {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  url?: string | null;
  logo?: string | null;
}

export type NewRedeSocial = Omit<IRedeSocial, 'id'> & { id: null };
