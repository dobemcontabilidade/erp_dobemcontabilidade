export interface IFluxoExecucao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
}

export type NewFluxoExecucao = Omit<IFluxoExecucao, 'id'> & { id: null };
