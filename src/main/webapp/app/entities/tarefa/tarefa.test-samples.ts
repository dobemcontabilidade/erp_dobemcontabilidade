import dayjs from 'dayjs/esm';

import { ITarefa, NewTarefa } from './tarefa.model';

export const sampleWithRequiredData: ITarefa = {
  id: 11563,
};

export const sampleWithPartialData: ITarefa = {
  id: 11404,
  titulo: 'whereas schoolhouse',
  diaUtil: false,
  geraMulta: true,
};

export const sampleWithFullData: ITarefa = {
  id: 20914,
  titulo: 'while',
  numeroDias: 29352,
  diaUtil: false,
  valor: 25823.15,
  notificarCliente: false,
  geraMulta: false,
  exibirEmpresa: true,
  dataLegal: dayjs('2024-08-09T23:57'),
  pontos: 25518,
  tipoTarefa: 'ORDEM_SERVICO',
};

export const sampleWithNewData: NewTarefa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
