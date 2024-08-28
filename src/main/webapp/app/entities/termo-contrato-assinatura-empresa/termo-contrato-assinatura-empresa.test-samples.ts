import dayjs from 'dayjs/esm';

import { ITermoContratoAssinaturaEmpresa, NewTermoContratoAssinaturaEmpresa } from './termo-contrato-assinatura-empresa.model';

export const sampleWithRequiredData: ITermoContratoAssinaturaEmpresa = {
  id: 5517,
};

export const sampleWithPartialData: ITermoContratoAssinaturaEmpresa = {
  id: 4609,
  dataAssinatura: dayjs('2024-08-28T10:25'),
  dataEnvioEmail: dayjs('2024-08-28T03:49'),
  urlDocumentoAssinado: '../fake-data/blob/hipster.txt',
  situacao: 'ASSINADO',
};

export const sampleWithFullData: ITermoContratoAssinaturaEmpresa = {
  id: 4356,
  dataAssinatura: dayjs('2024-08-28T12:53'),
  dataEnvioEmail: dayjs('2024-08-28T13:51'),
  urlDocumentoAssinado: '../fake-data/blob/hipster.txt',
  situacao: 'ASSINADO',
};

export const sampleWithNewData: NewTermoContratoAssinaturaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
