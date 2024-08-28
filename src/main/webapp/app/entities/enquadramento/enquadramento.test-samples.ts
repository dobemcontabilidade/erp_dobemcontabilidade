import { IEnquadramento, NewEnquadramento } from './enquadramento.model';

export const sampleWithRequiredData: IEnquadramento = {
  id: 17823,
};

export const sampleWithPartialData: IEnquadramento = {
  id: 29245,
  nome: 'all yum yowza',
  sigla: 'pish once ew',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IEnquadramento = {
  id: 3419,
  nome: 'brr green',
  sigla: 'sally',
  limiteInicial: 8154.76,
  limiteFinal: 7435.99,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewEnquadramento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
