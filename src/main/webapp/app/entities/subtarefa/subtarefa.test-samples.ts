import { ISubtarefa, NewSubtarefa } from './subtarefa.model';

export const sampleWithRequiredData: ISubtarefa = {
  id: 22273,
};

export const sampleWithPartialData: ISubtarefa = {
  id: 24080,
  item: 'yoga along reassuringly',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ISubtarefa = {
  id: 6042,
  ordem: 27704,
  item: 'in coolly',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewSubtarefa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
