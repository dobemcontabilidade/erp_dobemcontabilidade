import { IProfissao, NewProfissao } from './profissao.model';

export const sampleWithRequiredData: IProfissao = {
  id: 10241,
};

export const sampleWithPartialData: IProfissao = {
  id: 445,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IProfissao = {
  id: 19959,
  nome: 'suspiciously buffet',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewProfissao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
