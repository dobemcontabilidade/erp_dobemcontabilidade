import { IPeriodoPagamento, NewPeriodoPagamento } from './periodo-pagamento.model';

export const sampleWithRequiredData: IPeriodoPagamento = {
  id: 4859,
};

export const sampleWithPartialData: IPeriodoPagamento = {
  id: 24908,
  periodo: 'win',
  numeroDias: 25426,
};

export const sampleWithFullData: IPeriodoPagamento = {
  id: 28243,
  periodo: 'crafty anywhere',
  numeroDias: 27629,
  idPlanGnet: 'who hm',
};

export const sampleWithNewData: NewPeriodoPagamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
