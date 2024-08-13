import { IValorBaseRamo, NewValorBaseRamo } from './valor-base-ramo.model';

export const sampleWithRequiredData: IValorBaseRamo = {
  id: 16986,
  valorBase: 21419.76,
};

export const sampleWithPartialData: IValorBaseRamo = {
  id: 19348,
  valorBase: 4439.6,
};

export const sampleWithFullData: IValorBaseRamo = {
  id: 24495,
  valorBase: 28416.26,
};

export const sampleWithNewData: NewValorBaseRamo = {
  valorBase: 9280.25,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
