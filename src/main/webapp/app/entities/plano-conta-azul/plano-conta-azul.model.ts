import { SituacaoPlanoContaAzulEnum } from 'app/entities/enumerations/situacao-plano-conta-azul-enum.model';

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
  situacao?: keyof typeof SituacaoPlanoContaAzulEnum | null;
}

export type NewPlanoContaAzul = Omit<IPlanoContaAzul, 'id'> & { id: null };
