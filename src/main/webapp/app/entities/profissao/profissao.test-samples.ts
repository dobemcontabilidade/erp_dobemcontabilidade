import { IProfissao, NewProfissao } from './profissao.model';

export const sampleWithRequiredData: IProfissao = {
  id: 23864,
};

export const sampleWithPartialData: IProfissao = {
  id: 21265,
  cbo: 28648,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IProfissao = {
  id: 25935,
  nome: 'gosh jubilant blah',
  cbo: 3898,
  categoria: 'rotten apud',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewProfissao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
