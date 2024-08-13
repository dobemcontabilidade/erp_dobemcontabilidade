import { IDepartamento, NewDepartamento } from './departamento.model';

export const sampleWithRequiredData: IDepartamento = {
  id: 31582,
};

export const sampleWithPartialData: IDepartamento = {
  id: 27613,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDepartamento = {
  id: 10585,
  nome: 'nitrogen after when',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDepartamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
