import { IAnexoRequeridoEmpresa, NewAnexoRequeridoEmpresa } from './anexo-requerido-empresa.model';

export const sampleWithRequiredData: IAnexoRequeridoEmpresa = {
  id: 25717,
};

export const sampleWithPartialData: IAnexoRequeridoEmpresa = {
  id: 20982,
  urlArquivo: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IAnexoRequeridoEmpresa = {
  id: 5537,
  obrigatorio: true,
  urlArquivo: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAnexoRequeridoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
