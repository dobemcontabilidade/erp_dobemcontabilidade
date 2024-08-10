import { IPerfilContador, NewPerfilContador } from './perfil-contador.model';

export const sampleWithRequiredData: IPerfilContador = {
  id: 136,
  perfil: 'yum around',
};

export const sampleWithPartialData: IPerfilContador = {
  id: 23101,
  perfil: 'meh',
  limiteEmpresas: 22726,
  limiteDepartamentos: 32376,
  limiteAreaContabils: 46,
};

export const sampleWithFullData: IPerfilContador = {
  id: 12090,
  perfil: 'vice blab',
  descricao: 'whereas indeed huzzah',
  limiteEmpresas: 21628,
  limiteDepartamentos: 20695,
  limiteAreaContabils: 18853,
  limiteFaturamento: 32461.09,
};

export const sampleWithNewData: NewPerfilContador = {
  perfil: 'fatally up sour',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
