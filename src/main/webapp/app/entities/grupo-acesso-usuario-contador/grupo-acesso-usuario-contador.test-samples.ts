import dayjs from 'dayjs/esm';

import { IGrupoAcessoUsuarioContador, NewGrupoAcessoUsuarioContador } from './grupo-acesso-usuario-contador.model';

export const sampleWithRequiredData: IGrupoAcessoUsuarioContador = {
  id: 29358,
};

export const sampleWithPartialData: IGrupoAcessoUsuarioContador = {
  id: 4969,
  dataExpiracao: dayjs('2024-08-12T11:51'),
  ilimitado: true,
};

export const sampleWithFullData: IGrupoAcessoUsuarioContador = {
  id: 1435,
  dataExpiracao: dayjs('2024-08-12T15:31'),
  ilimitado: false,
  desabilitar: false,
};

export const sampleWithNewData: NewGrupoAcessoUsuarioContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
