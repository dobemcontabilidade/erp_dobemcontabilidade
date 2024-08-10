export interface IDepartamento {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewDepartamento = Omit<IDepartamento, 'id'> & { id: null };
