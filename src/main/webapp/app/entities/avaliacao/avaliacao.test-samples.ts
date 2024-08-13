import { IAvaliacao, NewAvaliacao } from './avaliacao.model';

export const sampleWithRequiredData: IAvaliacao = {
  id: 3710,
};

export const sampleWithPartialData: IAvaliacao = {
  id: 11109,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IAvaliacao = {
  id: 14170,
  nome: 'yearningly over',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAvaliacao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
