import { IAnexoRequerido, NewAnexoRequerido } from './anexo-requerido.model';

export const sampleWithRequiredData: IAnexoRequerido = {
  id: 613,
  nome: 'whose aboard trivial',
};

export const sampleWithPartialData: IAnexoRequerido = {
  id: 11493,
  nome: 'consequently consent',
  tipo: 'PF',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IAnexoRequerido = {
  id: 7557,
  nome: 'which',
  tipo: 'PF',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAnexoRequerido = {
  nome: 'subgroup',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
