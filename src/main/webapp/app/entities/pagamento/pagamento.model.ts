import dayjs from 'dayjs/esm';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { SituacaoPagamentoEnum } from 'app/entities/enumerations/situacao-pagamento-enum.model';

export interface IPagamento {
  id: number;
  dataCobranca?: dayjs.Dayjs | null;
  dataVencimento?: dayjs.Dayjs | null;
  dataPagamento?: dayjs.Dayjs | null;
  valorPago?: number | null;
  valorCobrado?: number | null;
  acrescimo?: number | null;
  multa?: number | null;
  juros?: number | null;
  situacao?: keyof typeof SituacaoPagamentoEnum | null;
  assinaturaEmpresa?: IAssinaturaEmpresa | null;
}

export type NewPagamento = Omit<IPagamento, 'id'> & { id: null };
