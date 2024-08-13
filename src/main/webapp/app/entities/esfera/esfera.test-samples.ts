import { IEsfera, NewEsfera } from './esfera.model';

export const sampleWithRequiredData: IEsfera = {
  id: 20552,
};

export const sampleWithPartialData: IEsfera = {
  id: 18441,
};

export const sampleWithFullData: IEsfera = {
  id: 739,
  nome: 'where qua',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewEsfera = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
