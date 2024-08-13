import { IAreaContabil, NewAreaContabil } from './area-contabil.model';

export const sampleWithRequiredData: IAreaContabil = {
  id: 10631,
};

export const sampleWithPartialData: IAreaContabil = {
  id: 4247,
  percentual: 11352.5,
};

export const sampleWithFullData: IAreaContabil = {
  id: 3999,
  nome: 'consequently',
  descricao: 'aha',
  percentual: 3878.94,
};

export const sampleWithNewData: NewAreaContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
