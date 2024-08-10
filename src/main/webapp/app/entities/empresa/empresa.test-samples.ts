import dayjs from 'dayjs/esm';

import { IEmpresa, NewEmpresa } from './empresa.model';

export const sampleWithRequiredData: IEmpresa = {
  id: 12369,
  razaoSocial: 'paint puzzled perform',
  nomeFantasia: 'gee',
};

export const sampleWithPartialData: IEmpresa = {
  id: 13833,
  razaoSocial: 'affix',
  nomeFantasia: 'energetically so',
  cnpj: 'kindheartedly near',
  urlContratoSocial: 'yum',
};

export const sampleWithFullData: IEmpresa = {
  id: 31099,
  razaoSocial: 'fatally',
  nomeFantasia: 'rouse of following',
  descricaoDoNegocio: '../fake-data/blob/hipster.txt',
  cnpj: 'loving ringworm once',
  dataAbertura: dayjs('2024-08-09T13:32'),
  urlContratoSocial: 'although because ew',
  capitalSocial: 14917.48,
};

export const sampleWithNewData: NewEmpresa = {
  razaoSocial: 'absent',
  nomeFantasia: 'blouse where',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
