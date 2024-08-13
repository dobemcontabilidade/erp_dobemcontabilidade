import { ISecaoCnae, NewSecaoCnae } from './secao-cnae.model';

export const sampleWithRequiredData: ISecaoCnae = {
  id: 9316,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ISecaoCnae = {
  id: 7344,
  codigo: 'for circa',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ISecaoCnae = {
  id: 1670,
  codigo: 'and',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewSecaoCnae = {
  descricao: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
