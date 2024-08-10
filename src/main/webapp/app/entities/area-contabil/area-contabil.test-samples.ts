import { IAreaContabil, NewAreaContabil } from './area-contabil.model';

export const sampleWithRequiredData: IAreaContabil = {
  id: 14682,
};

export const sampleWithPartialData: IAreaContabil = {
  id: 19882,
};

export const sampleWithFullData: IAreaContabil = {
  id: 15901,
  nome: 'furthermore tug formal',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAreaContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
