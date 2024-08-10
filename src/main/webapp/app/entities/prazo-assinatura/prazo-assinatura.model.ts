export interface IPrazoAssinatura {
  id: number;
  prazo?: string | null;
  meses?: number | null;
}

export type NewPrazoAssinatura = Omit<IPrazoAssinatura, 'id'> & { id: null };
