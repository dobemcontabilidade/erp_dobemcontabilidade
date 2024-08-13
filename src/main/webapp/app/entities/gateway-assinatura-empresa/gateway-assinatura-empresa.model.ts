import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { IGatewayPagamento } from 'app/entities/gateway-pagamento/gateway-pagamento.model';

export interface IGatewayAssinaturaEmpresa {
  id: number;
  ativo?: boolean | null;
  codigoAssinatura?: string | null;
  assinaturaEmpresa?: IAssinaturaEmpresa | null;
  gatewayPagamento?: IGatewayPagamento | null;
}

export type NewGatewayAssinaturaEmpresa = Omit<IGatewayAssinaturaEmpresa, 'id'> & { id: null };
