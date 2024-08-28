import { IRedeSocialEmpresa, NewRedeSocialEmpresa } from './rede-social-empresa.model';

export const sampleWithRequiredData: IRedeSocialEmpresa = {
  id: 7199,
  perfil: 'ack tempting overshoot',
};

export const sampleWithPartialData: IRedeSocialEmpresa = {
  id: 21849,
  perfil: 'twin willing',
};

export const sampleWithFullData: IRedeSocialEmpresa = {
  id: 28193,
  perfil: 'blank aboard sidle',
  urlPerfil: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewRedeSocialEmpresa = {
  perfil: 'refrigerate exactly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
