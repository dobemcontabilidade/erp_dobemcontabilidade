import { IPeriodoPagamento } from 'app/entities/periodo-pagamento/periodo-pagamento.model';
import { IPlanoContaAzul } from 'app/entities/plano-conta-azul/plano-conta-azul.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { IDescontoPlanoContabil } from 'app/entities/desconto-plano-contabil/desconto-plano-contabil.model';
import { IDescontoPlanoContaAzul } from 'app/entities/desconto-plano-conta-azul/desconto-plano-conta-azul.model';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';

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
  valorPlanoContaAzulComDesconto?: number | null;
  valorMensalidade?: number | null;
  valorPeriodo?: number | null;
  valorAno?: number | null;
  periodoPagamento?: IPeriodoPagamento | null;
  planoContaAzul?: IPlanoContaAzul | null;
  planoContabil?: IPlanoContabil | null;
  ramo?: IRamo | null;
  tributacao?: ITributacao | null;
  descontoPlanoContabil?: IDescontoPlanoContabil | null;
  descontoPlanoContaAzul?: IDescontoPlanoContaAzul | null;
  assinaturaEmpresa?: IAssinaturaEmpresa | null;
}

export type NewCalculoPlanoAssinatura = Omit<ICalculoPlanoAssinatura, 'id'> & { id: null };
