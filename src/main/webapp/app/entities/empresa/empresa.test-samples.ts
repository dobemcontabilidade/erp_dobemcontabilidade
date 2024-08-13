import dayjs from 'dayjs/esm';

import { IEmpresa, NewEmpresa } from './empresa.model';

export const sampleWithRequiredData: IEmpresa = {
  id: 8435,
  razaoSocial: 'outside wrong um',
  nomeFantasia: 'rigidly lightly',
};

export const sampleWithPartialData: IEmpresa = {
  id: 2892,
  razaoSocial: 'utilization interesting',
  nomeFantasia: 'via honour',
  cnpj: 'juice wildly',
  capitalSocial: 25329.48,
};

export const sampleWithFullData: IEmpresa = {
  id: 11903,
  razaoSocial: 'though hesitate torch',
  nomeFantasia: 'bah',
  descricaoDoNegocio: '../fake-data/blob/hipster.txt',
  cnpj: 'roundabout for knowl',
  dataAbertura: dayjs('2024-08-12T13:01'),
  urlContratoSocial: 'yahoo ha',
  capitalSocial: 10793.62,
};

export const sampleWithNewData: NewEmpresa = {
  razaoSocial: 'yippee invigilate among',
  nomeFantasia: 'but ravel',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
