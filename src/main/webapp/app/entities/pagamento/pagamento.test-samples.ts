import dayjs from 'dayjs/esm';

import { IPagamento, NewPagamento } from './pagamento.model';

export const sampleWithRequiredData: IPagamento = {
  id: 17778,
  situacao: 'PAGO',
};

export const sampleWithPartialData: IPagamento = {
  id: 516,
  dataCobranca: dayjs('2024-08-09T15:03'),
  dataPagamento: dayjs('2024-08-09T21:40'),
  valorCobrado: 7627.09,
  juros: 16582.37,
  situacao: 'PAGO',
};

export const sampleWithFullData: IPagamento = {
  id: 31248,
  dataCobranca: dayjs('2024-08-09T20:13'),
  dataVencimento: dayjs('2024-08-09T15:03'),
  dataPagamento: dayjs('2024-08-09T15:53'),
  valorPago: 24915.04,
  valorCobrado: 8053.13,
  acrescimo: 25558.73,
  multa: 21879.39,
  juros: 18852.65,
  situacao: 'PAGO',
};

export const sampleWithNewData: NewPagamento = {
  situacao: 'EM_ATRASO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
