import { IGrupoAcessoPadrao, NewGrupoAcessoPadrao } from './grupo-acesso-padrao.model';

export const sampleWithRequiredData: IGrupoAcessoPadrao = {
  id: 5104,
};

export const sampleWithPartialData: IGrupoAcessoPadrao = {
  id: 27760,
  nome: 'woot while despite',
};

export const sampleWithFullData: IGrupoAcessoPadrao = {
  id: 18466,
  nome: 'fret',
};

export const sampleWithNewData: NewGrupoAcessoPadrao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
