export interface IAtorAvaliado {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  ativo?: boolean | null;
}

export type NewAtorAvaliado = Omit<IAtorAvaliado, 'id'> & { id: null };
