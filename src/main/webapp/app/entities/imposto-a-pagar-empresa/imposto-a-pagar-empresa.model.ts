import dayjs from 'dayjs/esm';
import { IImpostoEmpresa } from 'app/entities/imposto-empresa/imposto-empresa.model';
import { SituacaoPagamentoImpostoEnum } from 'app/entities/enumerations/situacao-pagamento-imposto-enum.model';

export interface IImpostoAPagarEmpresa {
  id: number;
  dataVencimento?: dayjs.Dayjs | null;
  dataPagamento?: dayjs.Dayjs | null;
  valor?: number | null;
  valorMulta?: number | null;
  urlArquivoPagamento?: string | null;
  urlArquivoComprovante?: string | null;
  situacaoPagamentoImpostoEnum?: keyof typeof SituacaoPagamentoImpostoEnum | null;
  imposto?: IImpostoEmpresa | null;
}

export type NewImpostoAPagarEmpresa = Omit<IImpostoAPagarEmpresa, 'id'> & { id: null };
