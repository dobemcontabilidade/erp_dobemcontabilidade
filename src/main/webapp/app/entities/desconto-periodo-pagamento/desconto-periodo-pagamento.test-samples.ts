import { IDescontoPeriodoPagamento, NewDescontoPeriodoPagamento } from './desconto-periodo-pagamento.model';

export const sampleWithRequiredData: IDescontoPeriodoPagamento = {
  id: 15410,
};

export const sampleWithPartialData: IDescontoPeriodoPagamento = {
  id: 32313,
};

export const sampleWithFullData: IDescontoPeriodoPagamento = {
  id: 32687,
  percentual: 5515.3,
};

export const sampleWithNewData: NewDescontoPeriodoPagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
