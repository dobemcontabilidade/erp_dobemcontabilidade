import { IAdicionalTributacao, NewAdicionalTributacao } from './adicional-tributacao.model';

export const sampleWithRequiredData: IAdicionalTributacao = {
  id: 11462,
  valor: 17512.31,
};

export const sampleWithPartialData: IAdicionalTributacao = {
  id: 7905,
  valor: 20038.73,
};

export const sampleWithFullData: IAdicionalTributacao = {
  id: 4790,
  valor: 27850.96,
};

export const sampleWithNewData: NewAdicionalTributacao = {
  valor: 31692.54,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
