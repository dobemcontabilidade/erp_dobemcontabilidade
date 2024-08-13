import { IGrupoCnae, NewGrupoCnae } from './grupo-cnae.model';

export const sampleWithRequiredData: IGrupoCnae = {
  id: 16113,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IGrupoCnae = {
  id: 3659,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IGrupoCnae = {
  id: 27152,
  codigo: 'dimpled vitiate',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewGrupoCnae = {
  descricao: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
