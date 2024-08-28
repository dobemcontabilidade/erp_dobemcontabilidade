import { IAdicionalRamo, NewAdicionalRamo } from './adicional-ramo.model';

export const sampleWithRequiredData: IAdicionalRamo = {
  id: 26081,
  valor: 27914.49,
};

export const sampleWithPartialData: IAdicionalRamo = {
  id: 18582,
  valor: 2528.82,
};

export const sampleWithFullData: IAdicionalRamo = {
  id: 28445,
  valor: 4690.86,
};

export const sampleWithNewData: NewAdicionalRamo = {
  valor: 11464.8,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
