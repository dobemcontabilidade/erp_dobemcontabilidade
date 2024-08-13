export interface IPerfilAcesso {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewPerfilAcesso = Omit<IPerfilAcesso, 'id'> & { id: null };
