import { IEnquadramento, NewEnquadramento } from './enquadramento.model';

export const sampleWithRequiredData: IEnquadramento = {
  id: 23860,
};

export const sampleWithPartialData: IEnquadramento = {
  id: 31513,
  limiteFinal: 27131.23,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IEnquadramento = {
  id: 24133,
  nome: 'steel excluding',
  sigla: 'authority',
  limiteInicial: 23031.14,
  limiteFinal: 16234.05,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewEnquadramento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
