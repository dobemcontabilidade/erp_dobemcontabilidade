import dayjs from 'dayjs/esm';

import { IEmpresa, NewEmpresa } from './empresa.model';

export const sampleWithRequiredData: IEmpresa = {
  id: 18019,
  razaoSocial: 'including selfishly geez',
  nomeFantasia: 'optimistically',
};

export const sampleWithPartialData: IEmpresa = {
  id: 29694,
  razaoSocial: 'phew because',
  nomeFantasia: 'pish that client',
  descricaoDoNegocio: '../fake-data/blob/hipster.txt',
  dataAbertura: dayjs('2024-08-09T13:19'),
  urlContratoSocial: 'ugh tech',
};

export const sampleWithFullData: IEmpresa = {
  id: 3199,
  razaoSocial: 'scratchy brilliant',
  nomeFantasia: 'without',
  descricaoDoNegocio: '../fake-data/blob/hipster.txt',
  cnpj: 'elegantly before',
  dataAbertura: dayjs('2024-08-09T02:22'),
  urlContratoSocial: 'cruise tenet',
  capitalSocial: 13749.39,
};

export const sampleWithNewData: NewEmpresa = {
  razaoSocial: 'lacquer bran',
  nomeFantasia: 'for thankfully',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
