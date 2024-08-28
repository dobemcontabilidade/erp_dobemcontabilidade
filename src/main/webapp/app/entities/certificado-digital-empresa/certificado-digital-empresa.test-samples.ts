import dayjs from 'dayjs/esm';

import { ICertificadoDigitalEmpresa, NewCertificadoDigitalEmpresa } from './certificado-digital-empresa.model';

export const sampleWithRequiredData: ICertificadoDigitalEmpresa = {
  id: 32035,
  urlCertificado: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ICertificadoDigitalEmpresa = {
  id: 7556,
  urlCertificado: '../fake-data/blob/hipster.txt',
  dataContratacao: dayjs('2024-08-28T02:07'),
  diasUso: 24106,
};

export const sampleWithFullData: ICertificadoDigitalEmpresa = {
  id: 27500,
  urlCertificado: '../fake-data/blob/hipster.txt',
  dataContratacao: dayjs('2024-08-28T14:41'),
  dataVencimento: dayjs('2024-08-28T03:18'),
  diasUso: 20756,
};

export const sampleWithNewData: NewCertificadoDigitalEmpresa = {
  urlCertificado: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
