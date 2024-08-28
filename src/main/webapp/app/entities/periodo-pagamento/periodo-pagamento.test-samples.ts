import { IPeriodoPagamento, NewPeriodoPagamento } from './periodo-pagamento.model';

export const sampleWithRequiredData: IPeriodoPagamento = {
  id: 12655,
};

export const sampleWithPartialData: IPeriodoPagamento = {
  id: 17873,
};

export const sampleWithFullData: IPeriodoPagamento = {
  id: 23921,
  periodo: 'swivel',
  numeroDias: 30544,
};

export const sampleWithNewData: NewPeriodoPagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
