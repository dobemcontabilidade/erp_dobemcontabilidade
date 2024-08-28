export interface IPessoajuridica {
  id: number;
  razaoSocial?: string | null;
  nomeFantasia?: string | null;
  cnpj?: string | null;
}

export type NewPessoajuridica = Omit<IPessoajuridica, 'id'> & { id: null };
