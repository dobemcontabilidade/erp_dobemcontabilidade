import { IPerfilRedeSocial, NewPerfilRedeSocial } from './perfil-rede-social.model';

export const sampleWithRequiredData: IPerfilRedeSocial = {
  id: 12521,
  rede: 'mmm whoever',
  urlPerfil: 'interestingly mRNA ally',
};

export const sampleWithPartialData: IPerfilRedeSocial = {
  id: 14845,
  rede: 'around anenst',
  urlPerfil: 'whether',
  tipoRede: 'OUTROS',
};

export const sampleWithFullData: IPerfilRedeSocial = {
  id: 6906,
  rede: 'enormous than',
  urlPerfil: 'the dob abaft',
  tipoRede: 'YOUTUBE',
};

export const sampleWithNewData: NewPerfilRedeSocial = {
  rede: 'beneath superpose',
  urlPerfil: 'epitomise',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
