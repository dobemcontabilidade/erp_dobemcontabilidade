import { ITributacao, NewTributacao } from './tributacao.model';

export const sampleWithRequiredData: ITributacao = {
  id: 22364,
};

export const sampleWithPartialData: ITributacao = {
  id: 3258,
  nome: 'top',
  descricao: '../fake-data/blob/hipster.txt',
  situacao: 'INATIVO',
};

export const sampleWithFullData: ITributacao = {
  id: 28350,
  nome: 'unnaturally whenever',
  descricao: '../fake-data/blob/hipster.txt',
  situacao: 'ATIVO',
};

export const sampleWithNewData: NewTributacao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
