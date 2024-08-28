import { IAdicionalTributacao, NewAdicionalTributacao } from './adicional-tributacao.model';

export const sampleWithRequiredData: IAdicionalTributacao = {
  id: 7314,
  valor: 18814.09,
};

export const sampleWithPartialData: IAdicionalTributacao = {
  id: 2243,
  valor: 26624.69,
};

export const sampleWithFullData: IAdicionalTributacao = {
  id: 14351,
  valor: 17800.15,
};

export const sampleWithNewData: NewAdicionalTributacao = {
  valor: 16765.46,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
