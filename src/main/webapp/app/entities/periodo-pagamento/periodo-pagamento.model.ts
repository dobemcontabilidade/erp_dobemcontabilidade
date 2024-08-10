export interface IPeriodoPagamento {
  id: number;
  periodo?: string | null;
  numeroDias?: number | null;
  idPlanGnet?: string | null;
}

export type NewPeriodoPagamento = Omit<IPeriodoPagamento, 'id'> & { id: null };
