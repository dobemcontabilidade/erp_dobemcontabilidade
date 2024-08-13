import dayjs from 'dayjs/esm';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { IFormaDePagamento } from 'app/entities/forma-de-pagamento/forma-de-pagamento.model';
import { SituacaoCobrancaEnum } from 'app/entities/enumerations/situacao-cobranca-enum.model';

export interface ICobrancaEmpresa {
  id: number;
  dataCobranca?: dayjs.Dayjs | null;
  valorPago?: number | null;
  urlCobranca?: string | null;
  urlArquivo?: string | null;
  valorCobrado?: number | null;
  situacaoCobranca?: keyof typeof SituacaoCobrancaEnum | null;
  assinaturaEmpresa?: IAssinaturaEmpresa | null;
  formaDePagamento?: IFormaDePagamento | null;
}

export type NewCobrancaEmpresa = Omit<ICobrancaEmpresa, 'id'> & { id: null };
