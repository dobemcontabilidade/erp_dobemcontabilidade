export interface IAvaliacao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewAvaliacao = Omit<IAvaliacao, 'id'> & { id: null };
