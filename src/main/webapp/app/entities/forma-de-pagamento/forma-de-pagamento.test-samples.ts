import { IFormaDePagamento, NewFormaDePagamento } from './forma-de-pagamento.model';

export const sampleWithRequiredData: IFormaDePagamento = {
  id: 25915,
};

export const sampleWithPartialData: IFormaDePagamento = {
  id: 2190,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IFormaDePagamento = {
  id: 13059,
  forma: 'deployment hybridize',
  descricao: '../fake-data/blob/hipster.txt',
  disponivel: true,
};

export const sampleWithNewData: NewFormaDePagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
