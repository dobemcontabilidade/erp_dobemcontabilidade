import { IFuncionalidade, NewFuncionalidade } from './funcionalidade.model';

export const sampleWithRequiredData: IFuncionalidade = {
  id: 26642,
};

export const sampleWithPartialData: IFuncionalidade = {
  id: 24465,
  ativa: true,
};

export const sampleWithFullData: IFuncionalidade = {
  id: 5877,
  nome: 'blissfully indolent',
  ativa: false,
};

export const sampleWithNewData: NewFuncionalidade = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
