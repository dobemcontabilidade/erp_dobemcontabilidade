export interface IEsfera {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewEsfera = Omit<IEsfera, 'id'> & { id: null };
