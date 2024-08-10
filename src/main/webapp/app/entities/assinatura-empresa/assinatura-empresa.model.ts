import dayjs from 'dayjs/esm';
import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { IFormaDePagamento } from 'app/entities/forma-de-pagamento/forma-de-pagamento.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IPlanoContaAzul } from 'app/entities/plano-conta-azul/plano-conta-azul.model';
import { SituacaoContratoEmpresaEnum } from 'app/entities/enumerations/situacao-contrato-empresa-enum.model';
import { TipoContratoEnum } from 'app/entities/enumerations/tipo-contrato-enum.model';

export interface IAssinaturaEmpresa {
  id: number;
  codigoAssinatura?: string | null;
  valorEnquadramento?: number | null;
  valorTributacao?: number | null;
  valorRamo?: number | null;
  valorFuncionarios?: number | null;
  valorSocios?: number | null;
  valorFaturamento?: number | null;
  valorPlanoContabil?: number | null;
  valorPlanoContabilComDesconto?: number | null;
  valorMensalidade?: number | null;
  valorPeriodo?: number | null;
  valorAno?: number | null;
  dataContratacao?: dayjs.Dayjs | null;
  dataEncerramento?: dayjs.Dayjs | null;
  diaVencimento?: number | null;
  situacao?: keyof typeof SituacaoContratoEmpresaEnum | null;
  tipoContrato?: keyof typeof TipoContratoEnum | null;
  periodoPagamento?: Pick<IPeriodoPagamento, 'id' | 'periodo'> | null;
  formaDePagamento?: Pick<IFormaDePagamento, 'id' | 'forma'> | null;
  planoContabil?: Pick<IPlanoContabil, 'id' | 'nome'> | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
  planoContaAzul?: Pick<IPlanoContaAzul, 'id'> | null;
}

export type NewAssinaturaEmpresa = Omit<IAssinaturaEmpresa, 'id'> & { id: null };
