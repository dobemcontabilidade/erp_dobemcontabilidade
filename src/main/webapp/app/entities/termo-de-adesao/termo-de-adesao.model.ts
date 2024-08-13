export interface ITermoDeAdesao {
  id: number;
  titulo?: string | null;
  descricao?: string | null;
}

export type NewTermoDeAdesao = Omit<ITermoDeAdesao, 'id'> & { id: null };
