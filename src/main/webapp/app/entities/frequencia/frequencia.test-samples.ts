import { IFrequencia, NewFrequencia } from './frequencia.model';

export const sampleWithRequiredData: IFrequencia = {
  id: 13104,
};

export const sampleWithPartialData: IFrequencia = {
  id: 19076,
};

export const sampleWithFullData: IFrequencia = {
  id: 30636,
  nome: 'interview pfft wisely',
  descricao: '../fake-data/blob/hipster.txt',
  numeroDias: 195,
};

export const sampleWithNewData: NewFrequencia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
