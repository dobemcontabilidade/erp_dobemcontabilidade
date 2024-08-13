import dayjs from 'dayjs/esm';

import { IGrupoAcessoEmpresaUsuarioContador, NewGrupoAcessoEmpresaUsuarioContador } from './grupo-acesso-empresa-usuario-contador.model';

export const sampleWithRequiredData: IGrupoAcessoEmpresaUsuarioContador = {
  id: 17862,
};

export const sampleWithPartialData: IGrupoAcessoEmpresaUsuarioContador = {
  id: 29718,
  ilimitado: false,
};

export const sampleWithFullData: IGrupoAcessoEmpresaUsuarioContador = {
  id: 12439,
  nome: 'gee despite kindheartedly',
  dataExpiracao: dayjs('2024-08-12T18:27'),
  ilimitado: true,
  desabilitar: true,
};

export const sampleWithNewData: NewGrupoAcessoEmpresaUsuarioContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
