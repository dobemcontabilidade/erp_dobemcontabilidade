import { ITermoContratoContabil, NewTermoContratoContabil } from './termo-contrato-contabil.model';

export const sampleWithRequiredData: ITermoContratoContabil = {
  id: 15288,
};

export const sampleWithPartialData: ITermoContratoContabil = {
  id: 13484,
  documento: 'pfft growling',
};

export const sampleWithFullData: ITermoContratoContabil = {
  id: 19063,
  documento: 'solemnly against woot',
  descricao: '../fake-data/blob/hipster.txt',
  titulo: 'modest anoint',
};

export const sampleWithNewData: NewTermoContratoContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
