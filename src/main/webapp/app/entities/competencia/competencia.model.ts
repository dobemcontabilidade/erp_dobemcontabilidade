export interface ICompetencia {
  id: number;
  nome?: string | null;
  numero?: number | null;
}

export type NewCompetencia = Omit<ICompetencia, 'id'> & { id: null };
