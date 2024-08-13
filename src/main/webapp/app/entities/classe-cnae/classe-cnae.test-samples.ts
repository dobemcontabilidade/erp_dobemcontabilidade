import { IClasseCnae, NewClasseCnae } from './classe-cnae.model';

export const sampleWithRequiredData: IClasseCnae = {
  id: 9621,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IClasseCnae = {
  id: 30543,
  codigo: 'ew misguided',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IClasseCnae = {
  id: 26447,
  codigo: 'above enormousl',
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
