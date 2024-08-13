import dayjs from 'dayjs/esm';

import { IGrupoAcessoUsuarioEmpresa, NewGrupoAcessoUsuarioEmpresa } from './grupo-acesso-usuario-empresa.model';

export const sampleWithRequiredData: IGrupoAcessoUsuarioEmpresa = {
  id: 25635,
};

export const sampleWithPartialData: IGrupoAcessoUsuarioEmpresa = {
  id: 20312,
  nome: 'awkwardly',
  dataExpiracao: dayjs('2024-08-12T17:39'),
  ilimitado: true,
};

export const sampleWithFullData: IGrupoAcessoUsuarioEmpresa = {
  id: 852,
  nome: 'supposing',
  dataExpiracao: dayjs('2024-08-12T07:22'),
  ilimitado: true,
  desabilitar: false,
};

export const sampleWithNewData: NewGrupoAcessoUsuarioEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
