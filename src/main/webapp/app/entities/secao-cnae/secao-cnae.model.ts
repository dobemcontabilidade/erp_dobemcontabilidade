export interface ISecaoCnae {
  id: number;
  codigo?: string | null;
  descricao?: string | null;
}

export type NewSecaoCnae = Omit<ISecaoCnae, 'id'> & { id: null };
