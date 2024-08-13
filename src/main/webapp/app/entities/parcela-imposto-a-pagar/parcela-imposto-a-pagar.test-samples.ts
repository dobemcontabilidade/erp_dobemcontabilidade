import dayjs from 'dayjs/esm';

import { IParcelaImpostoAPagar, NewParcelaImpostoAPagar } from './parcela-imposto-a-pagar.model';

export const sampleWithRequiredData: IParcelaImpostoAPagar = {
  id: 26969,
};

export const sampleWithPartialData: IParcelaImpostoAPagar = {
  id: 17118,
  dataPagamento: dayjs('2024-08-13T02:06'),
  valor: 7822.94,
  urlArquivoPagamento: 'offensively however',
  urlArquivoComprovante: 'even happy',
  mesCompetencia: 'ABRIL',
};

export const sampleWithFullData: IParcelaImpostoAPagar = {
  id: 9983,
  numeroParcela: 7146,
  dataVencimento: dayjs('2024-08-12T08:35'),
  dataPagamento: dayjs('2024-08-13T00:51'),
  valor: 19406.42,
  valorMulta: 18242,
  urlArquivoPagamento: 'utterly dissent spinach',
  urlArquivoComprovante: 'press card whereas',
  mesCompetencia: 'SETEMBRO',
};

export const sampleWithNewData: NewParcelaImpostoAPagar = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
