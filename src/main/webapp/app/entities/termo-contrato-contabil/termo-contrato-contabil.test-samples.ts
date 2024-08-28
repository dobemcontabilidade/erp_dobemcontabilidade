import dayjs from 'dayjs/esm';

import { ITermoContratoContabil, NewTermoContratoContabil } from './termo-contrato-contabil.model';

export const sampleWithRequiredData: ITermoContratoContabil = {
  id: 6339,
};

export const sampleWithPartialData: ITermoContratoContabil = {
  id: 14298,
  descricao: '../fake-data/blob/hipster.txt',
  documento: '../fake-data/blob/hipster.txt',
  disponivel: true,
  dataCriacao: dayjs('2024-08-28T12:40'),
};

export const sampleWithFullData: ITermoContratoContabil = {
  id: 26219,
  titulo: 'keenly gah so',
  descricao: '../fake-data/blob/hipster.txt',
  urlDocumentoFonte: '../fake-data/blob/hipster.txt',
  documento: '../fake-data/blob/hipster.txt',
  disponivel: true,
  dataCriacao: dayjs('2024-08-28T05:03'),
};

export const sampleWithNewData: NewTermoContratoContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
