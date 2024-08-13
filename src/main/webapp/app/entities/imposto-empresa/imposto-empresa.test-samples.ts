import { IImpostoEmpresa, NewImpostoEmpresa } from './imposto-empresa.model';

export const sampleWithRequiredData: IImpostoEmpresa = {
  id: 19223,
};

export const sampleWithPartialData: IImpostoEmpresa = {
  id: 14476,
  diaVencimento: 23775,
};

export const sampleWithFullData: IImpostoEmpresa = {
  id: 13858,
  diaVencimento: 32325,
};

export const sampleWithNewData: NewImpostoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
