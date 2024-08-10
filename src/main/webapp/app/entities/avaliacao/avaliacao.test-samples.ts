import { IAvaliacao, NewAvaliacao } from './avaliacao.model';

export const sampleWithRequiredData: IAvaliacao = {
  id: 8800,
};

export const sampleWithPartialData: IAvaliacao = {
  id: 5319,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IAvaliacao = {
  id: 698,
  nome: 'part drinking afterwards',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAvaliacao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
