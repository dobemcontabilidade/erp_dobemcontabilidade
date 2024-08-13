import { IAdicionalRamo, NewAdicionalRamo } from './adicional-ramo.model';

export const sampleWithRequiredData: IAdicionalRamo = {
  id: 19074,
  valor: 17849.41,
};

export const sampleWithPartialData: IAdicionalRamo = {
  id: 26169,
  valor: 15284.72,
};

export const sampleWithFullData: IAdicionalRamo = {
  id: 11650,
  valor: 13104.72,
};

export const sampleWithNewData: NewAdicionalRamo = {
  valor: 25275.81,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
