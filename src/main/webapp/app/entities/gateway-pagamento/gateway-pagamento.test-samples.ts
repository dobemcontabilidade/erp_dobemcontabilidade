import { IGatewayPagamento, NewGatewayPagamento } from './gateway-pagamento.model';

export const sampleWithRequiredData: IGatewayPagamento = {
  id: 17690,
};

export const sampleWithPartialData: IGatewayPagamento = {
  id: 11814,
};

export const sampleWithFullData: IGatewayPagamento = {
  id: 23780,
  nome: 'impress drat',
  descricao: 'unto',
};

export const sampleWithNewData: NewGatewayPagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
