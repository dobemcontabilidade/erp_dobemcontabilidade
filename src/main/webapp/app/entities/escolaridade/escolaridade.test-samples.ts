import { IEscolaridade, NewEscolaridade } from './escolaridade.model';

export const sampleWithRequiredData: IEscolaridade = {
  id: 31390,
  nome: 'miserably',
};

export const sampleWithPartialData: IEscolaridade = {
  id: 17200,
  nome: 'gosh aspire boohoo',
};

export const sampleWithFullData: IEscolaridade = {
  id: 5816,
  nome: 'amidst',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewEscolaridade = {
  nome: 'separately',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
