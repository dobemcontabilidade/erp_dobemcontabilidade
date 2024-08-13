import { ITermoContratoContabil, NewTermoContratoContabil } from './termo-contrato-contabil.model';

export const sampleWithRequiredData: ITermoContratoContabil = {
  id: 24193,
};

export const sampleWithPartialData: ITermoContratoContabil = {
  id: 19884,
  linkTermo: 'since',
  titulo: 'ugh',
};

export const sampleWithFullData: ITermoContratoContabil = {
  id: 29290,
  linkTermo: 'sparrow',
  descricao: 'utter',
  titulo: 'at consequently hm',
};

export const sampleWithNewData: NewTermoContratoContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
