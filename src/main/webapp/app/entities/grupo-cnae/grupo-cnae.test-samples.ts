import { IGrupoCnae, NewGrupoCnae } from './grupo-cnae.model';

export const sampleWithRequiredData: IGrupoCnae = {
  id: 17193,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IGrupoCnae = {
  id: 30537,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IGrupoCnae = {
  id: 10721,
  codigo: 'gadzooks arena ',
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
