import { ISecaoCnae, NewSecaoCnae } from './secao-cnae.model';

export const sampleWithRequiredData: ISecaoCnae = {
  id: 29999,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ISecaoCnae = {
  id: 20665,
  codigo: 'woot',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ISecaoCnae = {
  id: 30326,
  codigo: 'indeed oh final',
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
