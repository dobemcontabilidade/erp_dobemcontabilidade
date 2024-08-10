import { ITermoDeAdesao, NewTermoDeAdesao } from './termo-de-adesao.model';

export const sampleWithRequiredData: ITermoDeAdesao = {
  id: 13020,
};

export const sampleWithPartialData: ITermoDeAdesao = {
  id: 9237,
  titulo: 'gloomy nor oh',
};

export const sampleWithFullData: ITermoDeAdesao = {
  id: 26224,
  titulo: 'the yet bisect',
  descricao: '../fake-data/blob/hipster.txt',
  urlDoc: 'pootle never',
};

export const sampleWithNewData: NewTermoDeAdesao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
