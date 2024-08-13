import { IPerfilContador, NewPerfilContador } from './perfil-contador.model';

export const sampleWithRequiredData: IPerfilContador = {
  id: 12462,
  perfil: 'rosy inasmuch',
};

export const sampleWithPartialData: IPerfilContador = {
  id: 24034,
  perfil: 'sphere to',
  limiteFaturamento: 849.27,
};

export const sampleWithFullData: IPerfilContador = {
  id: 29877,
  perfil: 'shadow',
  descricao: 'once left',
  limiteEmpresas: 29432,
  limiteDepartamentos: 22745,
  limiteFaturamento: 24524.47,
};

export const sampleWithNewData: NewPerfilContador = {
  perfil: 'far ballpark',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
