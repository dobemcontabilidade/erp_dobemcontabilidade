import dayjs from 'dayjs/esm';

import { ICobrancaEmpresa, NewCobrancaEmpresa } from './cobranca-empresa.model';

export const sampleWithRequiredData: ICobrancaEmpresa = {
  id: 6677,
};

export const sampleWithPartialData: ICobrancaEmpresa = {
  id: 6975,
  valorPago: 22919.24,
  valorCobrado: 11691.75,
  situacaoCobranca: 'ABERTA',
};

export const sampleWithFullData: ICobrancaEmpresa = {
  id: 10436,
  dataCobranca: dayjs('2024-08-13T03:14'),
  valorPago: 12925.25,
  urlCobranca: 'wildly phony',
  urlArquivo: 'duh',
  valorCobrado: 21732.02,
  situacaoCobranca: 'ATRASADA',
};

export const sampleWithNewData: NewCobrancaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
