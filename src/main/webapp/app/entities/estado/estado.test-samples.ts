import { IEstado, NewEstado } from './estado.model';

export const sampleWithRequiredData: IEstado = {
  id: 14511,
};

export const sampleWithPartialData: IEstado = {
  id: 25251,
  naturalidade: 'march knowledgeably meanwhile',
};

export const sampleWithFullData: IEstado = {
  id: 31655,
  nome: 'oh bloat lest',
  naturalidade: 'whenever',
  sigla: 'drat',
};

export const sampleWithNewData: NewEstado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
