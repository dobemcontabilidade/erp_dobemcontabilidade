import { IRamo, NewRamo } from './ramo.model';

export const sampleWithRequiredData: IRamo = {
  id: 30849,
};

export const sampleWithPartialData: IRamo = {
  id: 10934,
};

export const sampleWithFullData: IRamo = {
  id: 22531,
  nome: 'inspiration',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewRamo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
