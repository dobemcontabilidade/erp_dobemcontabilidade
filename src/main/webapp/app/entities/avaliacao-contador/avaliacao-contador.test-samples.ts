import { IAvaliacaoContador, NewAvaliacaoContador } from './avaliacao-contador.model';

export const sampleWithRequiredData: IAvaliacaoContador = {
  id: 25207,
};

export const sampleWithPartialData: IAvaliacaoContador = {
  id: 346,
};

export const sampleWithFullData: IAvaliacaoContador = {
  id: 4365,
  pontuacao: 30157.86,
};

export const sampleWithNewData: NewAvaliacaoContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
