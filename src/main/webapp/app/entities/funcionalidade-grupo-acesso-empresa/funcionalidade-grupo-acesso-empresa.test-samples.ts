import dayjs from 'dayjs/esm';

import { IFuncionalidadeGrupoAcessoEmpresa, NewFuncionalidadeGrupoAcessoEmpresa } from './funcionalidade-grupo-acesso-empresa.model';

export const sampleWithRequiredData: IFuncionalidadeGrupoAcessoEmpresa = {
  id: 8804,
};

export const sampleWithPartialData: IFuncionalidadeGrupoAcessoEmpresa = {
  id: 28034,
  dataExpiracao: dayjs('2024-08-12T11:41'),
  ilimitado: false,
};

export const sampleWithFullData: IFuncionalidadeGrupoAcessoEmpresa = {
  id: 4138,
  ativa: 'drafty',
  dataExpiracao: dayjs('2024-08-12T07:46'),
  ilimitado: false,
  desabilitar: true,
};

export const sampleWithNewData: NewFuncionalidadeGrupoAcessoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
