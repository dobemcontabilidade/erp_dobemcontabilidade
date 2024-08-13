import { IAvaliacaoContador, NewAvaliacaoContador } from './avaliacao-contador.model';

export const sampleWithRequiredData: IAvaliacaoContador = {
  id: 25495,
};

export const sampleWithPartialData: IAvaliacaoContador = {
  id: 29063,
};

export const sampleWithFullData: IAvaliacaoContador = {
  id: 3413,
  pontuacao: 9645.77,
};

export const sampleWithNewData: NewAvaliacaoContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
