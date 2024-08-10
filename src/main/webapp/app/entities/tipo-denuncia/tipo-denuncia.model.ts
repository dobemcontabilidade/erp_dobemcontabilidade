export interface ITipoDenuncia {
  id: number;
  tipo?: string | null;
  descricao?: string | null;
}

export type NewTipoDenuncia = Omit<ITipoDenuncia, 'id'> & { id: null };
