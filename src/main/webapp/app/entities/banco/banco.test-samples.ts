import { IBanco, NewBanco } from './banco.model';

export const sampleWithRequiredData: IBanco = {
  id: 6721,
  nome: 'sharply',
  codigo: 'revenge ferociously',
};

export const sampleWithPartialData: IBanco = {
  id: 20792,
  nome: 'psst',
  codigo: 'narrowcast',
};

export const sampleWithFullData: IBanco = {
  id: 23698,
  nome: 'festival pish',
  codigo: 'past',
};

export const sampleWithNewData: NewBanco = {
  nome: 'yahoo sticky athwart',
  codigo: 'urn variability pres',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
