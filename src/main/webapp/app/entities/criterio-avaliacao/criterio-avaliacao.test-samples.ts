import { ICriterioAvaliacao, NewCriterioAvaliacao } from './criterio-avaliacao.model';

export const sampleWithRequiredData: ICriterioAvaliacao = {
  id: 26121,
  nome: 'as',
};

export const sampleWithPartialData: ICriterioAvaliacao = {
  id: 766,
  nome: 'muffled sniffle',
  descricao: 'phew',
};

export const sampleWithFullData: ICriterioAvaliacao = {
  id: 6406,
  nome: 'off readdress blindfolded',
  descricao: 'outside noisily',
};

export const sampleWithNewData: NewCriterioAvaliacao = {
  nome: 'warmhearted warmth cruelly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
