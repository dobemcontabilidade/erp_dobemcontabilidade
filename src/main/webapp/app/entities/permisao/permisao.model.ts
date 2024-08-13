export interface IPermisao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  label?: string | null;
}

export type NewPermisao = Omit<IPermisao, 'id'> & { id: null };
