import { IFormaDePagamento, NewFormaDePagamento } from './forma-de-pagamento.model';

export const sampleWithRequiredData: IFormaDePagamento = {
  id: 22546,
};

export const sampleWithPartialData: IFormaDePagamento = {
  id: 12090,
  forma: 'why since',
};

export const sampleWithFullData: IFormaDePagamento = {
  id: 20697,
  forma: 'gadzooks down uh-huh',
  disponivel: true,
};

export const sampleWithNewData: NewFormaDePagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
