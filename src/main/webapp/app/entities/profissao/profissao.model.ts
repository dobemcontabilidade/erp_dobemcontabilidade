export interface IProfissao {
  id: number;
  nome?: string | null;
  cbo?: number | null;
  categoria?: string | null;
  descricao?: string | null;
}

export type NewProfissao = Omit<IProfissao, 'id'> & { id: null };
