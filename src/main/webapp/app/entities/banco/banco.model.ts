export interface IBanco {
  id: number;
  nome?: string | null;
  codigo?: string | null;
}

export type NewBanco = Omit<IBanco, 'id'> & { id: null };
