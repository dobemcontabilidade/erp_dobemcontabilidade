import { ICidade, NewCidade } from './cidade.model';

export const sampleWithRequiredData: ICidade = {
  id: 20864,
  nome: 'whenever attack resell',
};

export const sampleWithPartialData: ICidade = {
  id: 32248,
  nome: 'obnoxiously speedy',
  contratacao: true,
};

export const sampleWithFullData: ICidade = {
  id: 12474,
  nome: 'madly brightly behind',
  contratacao: true,
  abertura: true,
};

export const sampleWithNewData: NewCidade = {
  nome: 'like acidly fuzzy',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
