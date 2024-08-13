import { IPerfilRedeSocial, NewPerfilRedeSocial } from './perfil-rede-social.model';

export const sampleWithRequiredData: IPerfilRedeSocial = {
  id: 21454,
  rede: 'brr lightly kilometer',
  urlPerfil: 'ha super furiously',
};

export const sampleWithPartialData: IPerfilRedeSocial = {
  id: 16610,
  rede: 'truthfully brr unselfish',
  urlPerfil: 'donut atop',
  tipoRede: 'TWITTER',
};

export const sampleWithFullData: IPerfilRedeSocial = {
  id: 21459,
  rede: 'innocently because given',
  urlPerfil: 'knavishly aha',
  tipoRede: 'YOUTUBE',
};

export const sampleWithNewData: NewPerfilRedeSocial = {
  rede: 'ack of so',
  urlPerfil: 'shop blah',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
