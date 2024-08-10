export interface IRamo {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewRamo = Omit<IRamo, 'id'> & { id: null };
