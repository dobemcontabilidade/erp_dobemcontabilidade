import { IGatewayAssinaturaEmpresa, NewGatewayAssinaturaEmpresa } from './gateway-assinatura-empresa.model';

export const sampleWithRequiredData: IGatewayAssinaturaEmpresa = {
  id: 12892,
};

export const sampleWithPartialData: IGatewayAssinaturaEmpresa = {
  id: 149,
};

export const sampleWithFullData: IGatewayAssinaturaEmpresa = {
  id: 2577,
  ativo: false,
  codigoAssinatura: 'hmph phew',
};

export const sampleWithNewData: NewGatewayAssinaturaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
