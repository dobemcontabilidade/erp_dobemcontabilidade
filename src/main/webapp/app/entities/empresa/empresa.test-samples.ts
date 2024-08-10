import dayjs from 'dayjs/esm';

import { IEmpresa, NewEmpresa } from './empresa.model';

export const sampleWithRequiredData: IEmpresa = {
  id: 32156,
  razaoSocial: 'swoon',
  nomeFantasia: 'versus majority',
};

export const sampleWithPartialData: IEmpresa = {
  id: 15286,
  razaoSocial: 'interest into',
  nomeFantasia: 'faith blank whoa',
  cnpj: 'finally as',
  capitalSocial: 13339.95,
};

export const sampleWithFullData: IEmpresa = {
  id: 24384,
  razaoSocial: 'drat',
  nomeFantasia: 'er politely',
  descricaoDoNegocio: '../fake-data/blob/hipster.txt',
  cnpj: 'unhappy whenever yah',
  dataAbertura: dayjs('2024-08-10T03:16'),
  urlContratoSocial: 'properly program',
  capitalSocial: 5111.91,
  tipoSegmento: 'SERVICO',
};

export const sampleWithNewData: NewEmpresa = {
  razaoSocial: 'drat',
  nomeFantasia: 'absent',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
