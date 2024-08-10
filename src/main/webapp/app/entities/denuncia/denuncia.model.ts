export interface IDenuncia {
  id: number;
  titulo?: string | null;
  mensagem?: string | null;
  descricao?: string | null;
}

export type NewDenuncia = Omit<IDenuncia, 'id'> & { id: null };
