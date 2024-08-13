import { IEnquadramento, NewEnquadramento } from './enquadramento.model';

export const sampleWithRequiredData: IEnquadramento = {
  id: 4910,
};

export const sampleWithPartialData: IEnquadramento = {
  id: 29395,
  sigla: 'inwardly occasional',
  limiteInicial: 29162.54,
};

export const sampleWithFullData: IEnquadramento = {
  id: 30336,
  nome: 'fake eyeball provided',
  sigla: 'appointment webpage consequently',
  limiteInicial: 9124.69,
  limiteFinal: 22397.55,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewEnquadramento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
