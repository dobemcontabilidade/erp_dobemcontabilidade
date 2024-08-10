export interface IEnquadramento {
  id: number;
  nome?: string | null;
  sigla?: string | null;
  limiteInicial?: number | null;
  limiteFinal?: number | null;
  descricao?: string | null;
}

export type NewEnquadramento = Omit<IEnquadramento, 'id'> & { id: null };
