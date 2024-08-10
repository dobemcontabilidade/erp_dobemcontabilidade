import { IFrequencia, NewFrequencia } from './frequencia.model';

export const sampleWithRequiredData: IFrequencia = {
  id: 16151,
};

export const sampleWithPartialData: IFrequencia = {
  id: 10578,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IFrequencia = {
  id: 32493,
  nome: 'greatly norm',
  prioridade: 'midst aside gee',
  descricao: '../fake-data/blob/hipster.txt',
  numeroDias: 1647,
};

export const sampleWithNewData: NewFrequencia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
