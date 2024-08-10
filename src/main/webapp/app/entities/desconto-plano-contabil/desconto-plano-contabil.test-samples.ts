import { IDescontoPlanoContabil, NewDescontoPlanoContabil } from './desconto-plano-contabil.model';

export const sampleWithRequiredData: IDescontoPlanoContabil = {
  id: 19490,
  percentual: 29174.68,
};

export const sampleWithPartialData: IDescontoPlanoContabil = {
  id: 23925,
  percentual: 28741.04,
};

export const sampleWithFullData: IDescontoPlanoContabil = {
  id: 3889,
  percentual: 15612.38,
};

export const sampleWithNewData: NewDescontoPlanoContabil = {
  percentual: 29326.4,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
