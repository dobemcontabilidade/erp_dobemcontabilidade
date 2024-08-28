import { IAdicionalEnquadramento, NewAdicionalEnquadramento } from './adicional-enquadramento.model';

export const sampleWithRequiredData: IAdicionalEnquadramento = {
  id: 13100,
};

export const sampleWithPartialData: IAdicionalEnquadramento = {
  id: 27793,
};

export const sampleWithFullData: IAdicionalEnquadramento = {
  id: 30121,
  valor: 28685.96,
};

export const sampleWithNewData: NewAdicionalEnquadramento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
