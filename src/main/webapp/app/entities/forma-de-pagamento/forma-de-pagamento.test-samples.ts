import { IFormaDePagamento, NewFormaDePagamento } from './forma-de-pagamento.model';

export const sampleWithRequiredData: IFormaDePagamento = {
  id: 17350,
};

export const sampleWithPartialData: IFormaDePagamento = {
  id: 626,
};

export const sampleWithFullData: IFormaDePagamento = {
  id: 20319,
  forma: 'wetly',
  disponivel: false,
};

export const sampleWithNewData: NewFormaDePagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
