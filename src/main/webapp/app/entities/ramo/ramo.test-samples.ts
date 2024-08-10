import { IRamo, NewRamo } from './ramo.model';

export const sampleWithRequiredData: IRamo = {
  id: 18810,
};

export const sampleWithPartialData: IRamo = {
  id: 26846,
  nome: 'ugh entice although',
};

export const sampleWithFullData: IRamo = {
  id: 32369,
  nome: 'what selfishly',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewRamo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
