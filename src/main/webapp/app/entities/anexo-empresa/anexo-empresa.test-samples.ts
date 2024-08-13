import { IAnexoEmpresa, NewAnexoEmpresa } from './anexo-empresa.model';

export const sampleWithRequiredData: IAnexoEmpresa = {
  id: 17843,
  urlAnexo: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IAnexoEmpresa = {
  id: 584,
  urlAnexo: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IAnexoEmpresa = {
  id: 17460,
  urlAnexo: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAnexoEmpresa = {
  urlAnexo: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
