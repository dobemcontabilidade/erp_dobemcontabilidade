export interface IGatewayPagamento {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewGatewayPagamento = Omit<IGatewayPagamento, 'id'> & { id: null };
