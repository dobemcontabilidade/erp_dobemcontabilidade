import { IAdicionalTributacao, NewAdicionalTributacao } from './adicional-tributacao.model';

export const sampleWithRequiredData: IAdicionalTributacao = {
  id: 24956,
  valor: 10181.62,
};

export const sampleWithPartialData: IAdicionalTributacao = {
  id: 25488,
  valor: 28234.73,
};

export const sampleWithFullData: IAdicionalTributacao = {
  id: 390,
  valor: 9945.96,
};

export const sampleWithNewData: NewAdicionalTributacao = {
  valor: 21290.87,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
