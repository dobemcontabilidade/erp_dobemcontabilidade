import { IAdicionalRamo, NewAdicionalRamo } from './adicional-ramo.model';

export const sampleWithRequiredData: IAdicionalRamo = {
  id: 26197,
  valor: 32622.95,
};

export const sampleWithPartialData: IAdicionalRamo = {
  id: 23040,
  valor: 17731,
};

export const sampleWithFullData: IAdicionalRamo = {
  id: 19357,
  valor: 28049.95,
};

export const sampleWithNewData: NewAdicionalRamo = {
  valor: 21257.59,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
