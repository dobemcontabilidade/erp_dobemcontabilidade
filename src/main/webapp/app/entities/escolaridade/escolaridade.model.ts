export interface IEscolaridade {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewEscolaridade = Omit<IEscolaridade, 'id'> & { id: null };
