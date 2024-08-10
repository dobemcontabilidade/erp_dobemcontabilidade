export interface ICnae {
  id: number;
  codigo?: string | null;
  descricao?: string | null;
  anexo?: number | null;
  atendidoFreemium?: boolean | null;
  atendido?: boolean | null;
  optanteSimples?: boolean | null;
  categoria?: string | null;
}

export type NewCnae = Omit<ICnae, 'id'> & { id: null };
