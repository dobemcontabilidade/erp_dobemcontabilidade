import { ICidade, NewCidade } from './cidade.model';

export const sampleWithRequiredData: ICidade = {
  id: 13274,
  nome: 'indict gleefully',
};

export const sampleWithPartialData: ICidade = {
  id: 13366,
  nome: 'sprout huzzah certainly',
};

export const sampleWithFullData: ICidade = {
  id: 6714,
  nome: 'modernity circa since',
  contratacao: true,
  abertura: true,
};

export const sampleWithNewData: NewCidade = {
  nome: 'underneath disgorge solidly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
