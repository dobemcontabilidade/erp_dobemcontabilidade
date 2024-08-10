import { IEsfera, NewEsfera } from './esfera.model';

export const sampleWithRequiredData: IEsfera = {
  id: 30119,
};

export const sampleWithPartialData: IEsfera = {
  id: 2985,
  nome: 'manacle huzzah',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IEsfera = {
  id: 23367,
  nome: 'idealistic after',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewEsfera = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
