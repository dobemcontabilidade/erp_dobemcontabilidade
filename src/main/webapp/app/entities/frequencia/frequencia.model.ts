export interface IFrequencia {
  id: number;
  nome?: string | null;
  prioridade?: string | null;
  descricao?: string | null;
  numeroDias?: number | null;
}

export type NewFrequencia = Omit<IFrequencia, 'id'> & { id: null };
