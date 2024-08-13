import { IPeriodoPagamento, NewPeriodoPagamento } from './periodo-pagamento.model';

export const sampleWithRequiredData: IPeriodoPagamento = {
  id: 21203,
};

export const sampleWithPartialData: IPeriodoPagamento = {
  id: 2747,
  numeroDias: 29743,
};

export const sampleWithFullData: IPeriodoPagamento = {
  id: 30789,
  periodo: 'door indeed',
  numeroDias: 8738,
  idPlanGnet: 'through glass',
};

export const sampleWithNewData: NewPeriodoPagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
