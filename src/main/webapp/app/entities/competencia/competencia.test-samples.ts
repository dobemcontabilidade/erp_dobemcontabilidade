import { ICompetencia, NewCompetencia } from './competencia.model';

export const sampleWithRequiredData: ICompetencia = {
  id: 26059,
};

export const sampleWithPartialData: ICompetencia = {
  id: 23508,
  nome: 'what',
};

export const sampleWithFullData: ICompetencia = {
  id: 18507,
  nome: 'lest',
  numero: 14000,
};

export const sampleWithNewData: NewCompetencia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
