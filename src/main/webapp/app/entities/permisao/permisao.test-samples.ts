import { IPermisao, NewPermisao } from './permisao.model';

export const sampleWithRequiredData: IPermisao = {
  id: 1624,
};

export const sampleWithPartialData: IPermisao = {
  id: 19996,
  nome: 'phew nimble',
  label: 'closely',
};

export const sampleWithFullData: IPermisao = {
  id: 20968,
  nome: 'into inquisitively',
  descricao: 'minor even',
  label: 'mete',
};

export const sampleWithNewData: NewPermisao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
