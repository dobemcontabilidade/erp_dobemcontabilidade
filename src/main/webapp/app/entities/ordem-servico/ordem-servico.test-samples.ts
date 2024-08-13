import dayjs from 'dayjs/esm';

import { IOrdemServico, NewOrdemServico } from './ordem-servico.model';

export const sampleWithRequiredData: IOrdemServico = {
  id: 19972,
};

export const sampleWithPartialData: IOrdemServico = {
  id: 29555,
  valor: 7931.38,
  dataCriacao: dayjs('2024-08-12T08:24'),
  statusDaOS: 'EMATENDIMENTO',
  descricao: 'drat',
};

export const sampleWithFullData: IOrdemServico = {
  id: 12703,
  valor: 4676.79,
  prazo: dayjs('2024-08-12T16:50'),
  dataCriacao: dayjs('2024-08-12T10:04'),
  dataHoraCancelamento: dayjs('2024-08-13T03:22'),
  statusDaOS: 'CANCELADA',
  descricao: 'massive gosh',
};

export const sampleWithNewData: NewOrdemServico = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
