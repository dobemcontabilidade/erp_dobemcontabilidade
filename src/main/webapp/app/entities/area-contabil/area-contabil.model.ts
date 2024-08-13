export interface IAreaContabil {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  percentual?: number | null;
}

export type NewAreaContabil = Omit<IAreaContabil, 'id'> & { id: null };
