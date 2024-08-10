import { IDivisaoCnae, NewDivisaoCnae } from './divisao-cnae.model';

export const sampleWithRequiredData: IDivisaoCnae = {
  id: 31291,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IDivisaoCnae = {
  id: 5148,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDivisaoCnae = {
  id: 10400,
  codigo: 'anti',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDivisaoCnae = {
  descricao: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
