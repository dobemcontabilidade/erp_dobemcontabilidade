import { IAnexoEmpresa, NewAnexoEmpresa } from './anexo-empresa.model';

export const sampleWithRequiredData: IAnexoEmpresa = {
  id: 9449,
  urlAnexo: '../fake-data/blob/hipster.txt',
  tipo: 'finally trained',
};

export const sampleWithPartialData: IAnexoEmpresa = {
  id: 16679,
  urlAnexo: '../fake-data/blob/hipster.txt',
  tipo: 'defiantly',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IAnexoEmpresa = {
  id: 16118,
  urlAnexo: '../fake-data/blob/hipster.txt',
  tipo: 'wrapping blindly',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAnexoEmpresa = {
  urlAnexo: '../fake-data/blob/hipster.txt',
  tipo: 'ton nor',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
