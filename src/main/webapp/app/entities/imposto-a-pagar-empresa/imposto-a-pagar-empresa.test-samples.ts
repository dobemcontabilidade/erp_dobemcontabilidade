import dayjs from 'dayjs/esm';

import { IImpostoAPagarEmpresa, NewImpostoAPagarEmpresa } from './imposto-a-pagar-empresa.model';

export const sampleWithRequiredData: IImpostoAPagarEmpresa = {
  id: 10293,
  dataVencimento: dayjs('2024-08-12T14:49'),
  dataPagamento: dayjs('2024-08-12T07:26'),
};

export const sampleWithPartialData: IImpostoAPagarEmpresa = {
  id: 6027,
  dataVencimento: dayjs('2024-08-13T02:34'),
  dataPagamento: dayjs('2024-08-13T02:23'),
  valor: 28576.17,
  valorMulta: 15362,
  urlArquivoComprovante: 'geez even empty',
};

export const sampleWithFullData: IImpostoAPagarEmpresa = {
  id: 19944,
  dataVencimento: dayjs('2024-08-12T06:50'),
  dataPagamento: dayjs('2024-08-12T22:52'),
  valor: 1056.56,
  valorMulta: 17155,
  urlArquivoPagamento: 'following barring',
  urlArquivoComprovante: 'weakly',
  situacaoPagamentoImpostoEnum: 'ATRASADO',
};

export const sampleWithNewData: NewImpostoAPagarEmpresa = {
  dataVencimento: dayjs('2024-08-12T14:55'),
  dataPagamento: dayjs('2024-08-12T21:41'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
