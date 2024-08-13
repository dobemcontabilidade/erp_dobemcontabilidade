import dayjs from 'dayjs/esm';

import { IPagamento, NewPagamento } from './pagamento.model';

export const sampleWithRequiredData: IPagamento = {
  id: 28922,
  situacao: 'APAGAR',
};

export const sampleWithPartialData: IPagamento = {
  id: 2734,
  dataCobranca: dayjs('2024-08-12T20:58'),
  valorCobrado: 23341.7,
  juros: 13355.5,
  situacao: 'APAGAR',
};

export const sampleWithFullData: IPagamento = {
  id: 9199,
  dataCobranca: dayjs('2024-08-12T12:24'),
  dataVencimento: dayjs('2024-08-12T14:24'),
  dataPagamento: dayjs('2024-08-12T10:15'),
  valorPago: 2560.63,
  valorCobrado: 20079.52,
  acrescimo: 18313.54,
  multa: 11370.36,
  juros: 22987.35,
  situacao: 'APAGAR',
};

export const sampleWithNewData: NewPagamento = {
  situacao: 'EMATRASO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
