import { ITributacao, NewTributacao } from './tributacao.model';

export const sampleWithRequiredData: ITributacao = {
  id: 28657,
};

export const sampleWithPartialData: ITributacao = {
  id: 26826,
  situacao: 'EXCLUIDO',
};

export const sampleWithFullData: ITributacao = {
  id: 14426,
  nome: 'circa',
  descricao: '../fake-data/blob/hipster.txt',
  situacao: 'BLOQUEADO',
};

export const sampleWithNewData: NewTributacao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
