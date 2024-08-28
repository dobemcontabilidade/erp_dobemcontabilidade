export interface IFormaDePagamento {
  id: number;
  forma?: string | null;
  descricao?: string | null;
  disponivel?: boolean | null;
}

export type NewFormaDePagamento = Omit<IFormaDePagamento, 'id'> & { id: null };
