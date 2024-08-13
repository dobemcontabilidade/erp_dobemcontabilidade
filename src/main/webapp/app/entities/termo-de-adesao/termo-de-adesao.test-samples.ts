import { ITermoDeAdesao, NewTermoDeAdesao } from './termo-de-adesao.model';

export const sampleWithRequiredData: ITermoDeAdesao = {
  id: 276,
};

export const sampleWithPartialData: ITermoDeAdesao = {
  id: 4819,
};

export const sampleWithFullData: ITermoDeAdesao = {
  id: 14460,
  titulo: 'farrow sheepishly',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewTermoDeAdesao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
