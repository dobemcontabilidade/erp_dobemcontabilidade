import { SituacaoPlanoContaAzul } from 'app/entities/enumerations/situacao-plano-conta-azul.model';

export interface IPlanoContaAzul {
  id: number;
  nome?: string | null;
  valorBase?: number | null;
  usuarios?: number | null;
  boletos?: number | null;
  notaFiscalProduto?: number | null;
  notaFiscalServico?: number | null;
  notaFiscalCe?: number | null;
  suporte?: boolean | null;
  situacao?: keyof typeof SituacaoPlanoContaAzul | null;
}

export type NewPlanoContaAzul = Omit<IPlanoContaAzul, 'id'> & { id: null };
