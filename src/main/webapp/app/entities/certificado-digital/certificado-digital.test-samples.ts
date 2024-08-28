import { ICertificadoDigital, NewCertificadoDigital } from './certificado-digital.model';

export const sampleWithRequiredData: ICertificadoDigital = {
  id: 12057,
};

export const sampleWithPartialData: ICertificadoDigital = {
  id: 14084,
  nome: 'blanket',
  tipoCertificado: 'OUTROS',
};

export const sampleWithFullData: ICertificadoDigital = {
  id: 5946,
  nome: 'digitise',
  sigla: 'disapprove',
  descricao: '../fake-data/blob/hipster.txt',
  tipoCertificado: 'A',
};

export const sampleWithNewData: NewCertificadoDigital = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
