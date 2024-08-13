import { ISistema, NewSistema } from './sistema.model';

export const sampleWithRequiredData: ISistema = {
  id: 5940,
};

export const sampleWithPartialData: ISistema = {
  id: 1995,
};

export const sampleWithFullData: ISistema = {
  id: 32749,
  nome: 'embarrassed instance',
  descricao: 'bowl sternly',
};

export const sampleWithNewData: NewSistema = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
