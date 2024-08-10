import { IClasseCnae, NewClasseCnae } from './classe-cnae.model';

export const sampleWithRequiredData: IClasseCnae = {
  id: 7919,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IClasseCnae = {
  id: 2970,
  codigo: 'while underneat',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IClasseCnae = {
  id: 4574,
  codigo: 'shyly if',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewClasseCnae = {
  descricao: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
