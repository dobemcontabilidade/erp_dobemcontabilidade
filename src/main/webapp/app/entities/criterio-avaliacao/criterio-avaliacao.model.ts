export interface ICriterioAvaliacao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewCriterioAvaliacao = Omit<ICriterioAvaliacao, 'id'> & { id: null };
