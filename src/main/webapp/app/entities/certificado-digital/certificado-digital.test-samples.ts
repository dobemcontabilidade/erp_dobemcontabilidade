import dayjs from 'dayjs/esm';

import { ICertificadoDigital, NewCertificadoDigital } from './certificado-digital.model';

export const sampleWithRequiredData: ICertificadoDigital = {
  id: 23518,
  urlCertificado: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ICertificadoDigital = {
  id: 23493,
  urlCertificado: '../fake-data/blob/hipster.txt',
  tipoCertificado: 'A1',
};

export const sampleWithFullData: ICertificadoDigital = {
  id: 20162,
  urlCertificado: '../fake-data/blob/hipster.txt',
  dataContratacao: dayjs('2024-08-13T05:00'),
  validade: 11617,
  tipoCertificado: 'S',
};

export const sampleWithNewData: NewCertificadoDigital = {
  urlCertificado: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
