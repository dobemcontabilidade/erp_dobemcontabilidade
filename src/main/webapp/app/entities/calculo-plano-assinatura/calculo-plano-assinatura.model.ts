import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { IDescontoPlanoContabil } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil.model';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { IDescontoPlanoContaAzul } from 'app/entities/desconto-plano-conta-azul/desconto-plano-conta-azul.model';
import { IPlanoContaAzul } from 'app/entities/plano-conta-azul/plano-conta-azul.model';

export interface ICalculoPlanoAssinatura {
  id: number;
  codigoAtendimento?: string | null;
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
  periodoPagamento?: Pick<IPeriodoPagamento, 'id' | 'periodo'> | null;
  planoContabil?: Pick<IPlanoContabil, 'id' | 'nome'> | null;
  ramo?: Pick<IRamo, 'id' | 'nome'> | null;
  tributacao?: Pick<ITributacao, 'id' | 'nome'> | null;
  descontoPlanoContabil?: Pick<IDescontoPlanoContabil, 'id' | 'percentual'> | null;
  assinaturaEmpresa?: Pick<IAssinaturaEmpresa, 'id' | 'codigoAssinatura'> | null;
  descontoPlanoContaAzul?: Pick<IDescontoPlanoContaAzul, 'id'> | null;
  planoContaAzul?: Pick<IPlanoContaAzul, 'id'> | null;
}

export type NewCalculoPlanoAssinatura = Omit<ICalculoPlanoAssinatura, 'id'> & { id: null };
