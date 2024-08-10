import dayjs from 'dayjs/esm';

import { ICertificadoDigital, NewCertificadoDigital } from './certificado-digital.model';

export const sampleWithRequiredData: ICertificadoDigital = {
  id: 17503,
  urlCertificado: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ICertificadoDigital = {
  id: 8419,
  urlCertificado: '../fake-data/blob/hipster.txt',
  dataContratacao: dayjs('2024-08-09T19:29'),
};

export const sampleWithFullData: ICertificadoDigital = {
  id: 20803,
  urlCertificado: '../fake-data/blob/hipster.txt',
  dataContratacao: dayjs('2024-08-09T13:23'),
  validade: 1466,
  tipoCertificado: 'A3',
};

export const sampleWithNewData: NewCertificadoDigital = {
  urlCertificado: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
