export interface IFornecedorCertificado {
  id: number;
  razaoSocial?: string | null;
  sigla?: string | null;
  descricao?: string | null;
}

export type NewFornecedorCertificado = Omit<IFornecedorCertificado, 'id'> & { id: null };
