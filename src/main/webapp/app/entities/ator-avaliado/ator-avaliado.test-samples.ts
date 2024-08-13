import { IAtorAvaliado, NewAtorAvaliado } from './ator-avaliado.model';

export const sampleWithRequiredData: IAtorAvaliado = {
  id: 29924,
  nome: 'until',
};

export const sampleWithPartialData: IAtorAvaliado = {
  id: 32558,
  nome: 'alongside when versus',
  descricao: 'mild',
  ativo: true,
};

export const sampleWithFullData: IAtorAvaliado = {
  id: 17619,
  nome: 'hm',
  descricao: 'denitrify',
  ativo: true,
};

export const sampleWithNewData: NewAtorAvaliado = {
  nome: 'destroy lovable outside',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
