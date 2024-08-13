import { IPerfilAcesso, NewPerfilAcesso } from './perfil-acesso.model';

export const sampleWithRequiredData: IPerfilAcesso = {
  id: 29039,
};

export const sampleWithPartialData: IPerfilAcesso = {
  id: 17810,
};

export const sampleWithFullData: IPerfilAcesso = {
  id: 12222,
  nome: 'ew female',
  descricao: 'sigh aw psst',
};

export const sampleWithNewData: NewPerfilAcesso = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
