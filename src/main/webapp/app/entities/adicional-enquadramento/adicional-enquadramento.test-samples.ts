import { IAdicionalEnquadramento, NewAdicionalEnquadramento } from './adicional-enquadramento.model';

export const sampleWithRequiredData: IAdicionalEnquadramento = {
  id: 5308,
};

export const sampleWithPartialData: IAdicionalEnquadramento = {
  id: 3581,
  valor: 8963.59,
};

export const sampleWithFullData: IAdicionalEnquadramento = {
  id: 13212,
  valor: 24194.61,
};

export const sampleWithNewData: NewAdicionalEnquadramento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
