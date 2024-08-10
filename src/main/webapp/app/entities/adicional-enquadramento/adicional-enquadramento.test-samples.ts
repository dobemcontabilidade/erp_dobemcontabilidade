import { IAdicionalEnquadramento, NewAdicionalEnquadramento } from './adicional-enquadramento.model';

export const sampleWithRequiredData: IAdicionalEnquadramento = {
  id: 27805,
};

export const sampleWithPartialData: IAdicionalEnquadramento = {
  id: 31787,
};

export const sampleWithFullData: IAdicionalEnquadramento = {
  id: 22211,
  valor: 1908.97,
};

export const sampleWithNewData: NewAdicionalEnquadramento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
