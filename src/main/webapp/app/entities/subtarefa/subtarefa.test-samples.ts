import { ISubtarefa, NewSubtarefa } from './subtarefa.model';

export const sampleWithRequiredData: ISubtarefa = {
  id: 25238,
};

export const sampleWithPartialData: ISubtarefa = {
  id: 13008,
  item: 'freely reassuringly madly',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ISubtarefa = {
  id: 22541,
  ordem: 10426,
  item: 'boss',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewSubtarefa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
