import { IDescontoPlanoContabil, NewDescontoPlanoContabil } from './desconto-plano-contabil.model';

export const sampleWithRequiredData: IDescontoPlanoContabil = {
  id: 3568,
  percentual: 8435.07,
};

export const sampleWithPartialData: IDescontoPlanoContabil = {
  id: 0,
  percentual: 1621.67,
};

export const sampleWithFullData: IDescontoPlanoContabil = {
  id: 7075,
  percentual: 14020.23,
};

export const sampleWithNewData: NewDescontoPlanoContabil = {
  percentual: 12631.71,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
