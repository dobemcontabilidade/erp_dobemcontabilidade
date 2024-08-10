import { IPeriodoPagamento, NewPeriodoPagamento } from './periodo-pagamento.model';

export const sampleWithRequiredData: IPeriodoPagamento = {
  id: 22677,
};

export const sampleWithPartialData: IPeriodoPagamento = {
  id: 29246,
  numeroDias: 6348,
};

export const sampleWithFullData: IPeriodoPagamento = {
  id: 10616,
  periodo: 'which bonsai part',
  numeroDias: 30219,
  idPlanGnet: 'keenly who behind',
};

export const sampleWithNewData: NewPeriodoPagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
