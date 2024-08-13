export interface ISistema {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewSistema = Omit<ISistema, 'id'> & { id: null };
