import dayjs from 'dayjs/esm';

import { IEmpresa, NewEmpresa } from './empresa.model';

export const sampleWithRequiredData: IEmpresa = {
  id: 9595,
  razaoSocial: 'reality underneath burdensome',
  cnae: 'faithfully whimsical',
};

export const sampleWithPartialData: IEmpresa = {
  id: 3041,
  razaoSocial: 'enfranchise',
  dataAbertura: dayjs('2024-08-28T18:04'),
  capitalSocial: 20339.33,
  cnae: 'or powerfully cutover',
};

export const sampleWithFullData: IEmpresa = {
  id: 9456,
  razaoSocial: 'gadzooks classic',
  descricaoDoNegocio: '../fake-data/blob/hipster.txt',
  dataAbertura: dayjs('2024-08-28T06:21'),
  urlContratoSocial: 'er',
  capitalSocial: 11271.87,
  cnae: 'ack cobble knavishly',
};

export const sampleWithNewData: NewEmpresa = {
  razaoSocial: 'boo while metric',
  cnae: 'eventually in expose',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
