import { IRedeSocial, NewRedeSocial } from './rede-social.model';

export const sampleWithRequiredData: IRedeSocial = {
  id: 20866,
  nome: 'exhausted tough',
};

export const sampleWithPartialData: IRedeSocial = {
  id: 14484,
  nome: 'tracking frilly off',
  logo: 'outlandish past',
};

export const sampleWithFullData: IRedeSocial = {
  id: 8798,
  nome: 'intermesh',
  descricao: '../fake-data/blob/hipster.txt',
  url: '../fake-data/blob/hipster.txt',
  logo: 'tensely dramaturge',
};

export const sampleWithNewData: NewRedeSocial = {
  nome: 'wherever tempo digitize',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
