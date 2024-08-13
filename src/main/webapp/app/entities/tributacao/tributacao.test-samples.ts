import { ITributacao, NewTributacao } from './tributacao.model';

export const sampleWithRequiredData: ITributacao = {
  id: 32066,
};

export const sampleWithPartialData: ITributacao = {
  id: 20639,
  nome: 'hm boohoo',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ITributacao = {
  id: 8698,
  nome: 'functional even acidly',
  descricao: '../fake-data/blob/hipster.txt',
  situacao: 'EXCLUIDO',
};

export const sampleWithNewData: NewTributacao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
