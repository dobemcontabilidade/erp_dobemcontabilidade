import { IGrupoAcessoEmpresa, NewGrupoAcessoEmpresa } from './grupo-acesso-empresa.model';

export const sampleWithRequiredData: IGrupoAcessoEmpresa = {
  id: 28749,
};

export const sampleWithPartialData: IGrupoAcessoEmpresa = {
  id: 3248,
  nome: 'steel frightening although',
};

export const sampleWithFullData: IGrupoAcessoEmpresa = {
  id: 15455,
  nome: 'misfit',
};

export const sampleWithNewData: NewGrupoAcessoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
