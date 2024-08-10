import { ICompetencia, NewCompetencia } from './competencia.model';

export const sampleWithRequiredData: ICompetencia = {
  id: 15078,
};

export const sampleWithPartialData: ICompetencia = {
  id: 12165,
  nome: 'beware offensively unfortunately',
  numero: 26307,
};

export const sampleWithFullData: ICompetencia = {
  id: 7419,
  nome: 'laparoscope once',
  numero: 12652,
};

export const sampleWithNewData: NewCompetencia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
