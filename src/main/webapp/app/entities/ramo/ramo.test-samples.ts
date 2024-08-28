import { IRamo, NewRamo } from './ramo.model';

export const sampleWithRequiredData: IRamo = {
  id: 14032,
};

export const sampleWithPartialData: IRamo = {
  id: 14263,
};

export const sampleWithFullData: IRamo = {
  id: 1480,
  nome: 'oh',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewRamo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
