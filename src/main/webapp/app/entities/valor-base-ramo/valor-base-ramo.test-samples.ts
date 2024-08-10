import { IValorBaseRamo, NewValorBaseRamo } from './valor-base-ramo.model';

export const sampleWithRequiredData: IValorBaseRamo = {
  id: 15971,
  valorBase: 6661.62,
};

export const sampleWithPartialData: IValorBaseRamo = {
  id: 1188,
  valorBase: 27921.71,
};

export const sampleWithFullData: IValorBaseRamo = {
  id: 17959,
  valorBase: 3516.99,
};

export const sampleWithNewData: NewValorBaseRamo = {
  valorBase: 17197.61,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
