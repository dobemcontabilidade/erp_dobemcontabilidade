import dayjs from 'dayjs/esm';

import { IFuncionalidadeGrupoAcessoPadrao, NewFuncionalidadeGrupoAcessoPadrao } from './funcionalidade-grupo-acesso-padrao.model';

export const sampleWithRequiredData: IFuncionalidadeGrupoAcessoPadrao = {
  id: 30177,
};

export const sampleWithPartialData: IFuncionalidadeGrupoAcessoPadrao = {
  id: 1555,
  dataExpiracao: dayjs('2024-08-12T18:11'),
  dataAtribuicao: dayjs('2024-08-13T03:25'),
};

export const sampleWithFullData: IFuncionalidadeGrupoAcessoPadrao = {
  id: 32084,
  autorizado: true,
  dataExpiracao: dayjs('2024-08-12T16:37'),
  dataAtribuicao: dayjs('2024-08-12T06:58'),
};

export const sampleWithNewData: NewFuncionalidadeGrupoAcessoPadrao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
