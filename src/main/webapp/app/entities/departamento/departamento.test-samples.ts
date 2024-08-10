import { IDepartamento, NewDepartamento } from './departamento.model';

export const sampleWithRequiredData: IDepartamento = {
  id: 22235,
};

export const sampleWithPartialData: IDepartamento = {
  id: 14192,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDepartamento = {
  id: 28724,
  nome: 'gee apud celebrated',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDepartamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
