import { IDivisaoCnae, NewDivisaoCnae } from './divisao-cnae.model';

export const sampleWithRequiredData: IDivisaoCnae = {
  id: 15580,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IDivisaoCnae = {
  id: 12208,
  codigo: 'yahoo sic',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDivisaoCnae = {
  id: 15152,
  codigo: 'include',
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
