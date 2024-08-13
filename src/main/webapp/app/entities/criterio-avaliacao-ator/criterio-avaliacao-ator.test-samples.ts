import { ICriterioAvaliacaoAtor, NewCriterioAvaliacaoAtor } from './criterio-avaliacao-ator.model';

export const sampleWithRequiredData: ICriterioAvaliacaoAtor = {
  id: 29354,
};

export const sampleWithPartialData: ICriterioAvaliacaoAtor = {
  id: 7231,
  ativo: false,
};

export const sampleWithFullData: ICriterioAvaliacaoAtor = {
  id: 29975,
  descricao: 'coaxingly',
  ativo: false,
};

export const sampleWithNewData: NewCriterioAvaliacaoAtor = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
