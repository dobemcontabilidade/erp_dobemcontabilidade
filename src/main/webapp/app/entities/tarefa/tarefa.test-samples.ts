import dayjs from 'dayjs/esm';

import { ITarefa, NewTarefa } from './tarefa.model';

export const sampleWithRequiredData: ITarefa = {
  id: 14741,
};

export const sampleWithPartialData: ITarefa = {
  id: 96,
  titulo: 'abaft up disqualify',
  numeroDias: 11233,
  diaUtil: false,
  pontos: 8186,
  tipoTarefa: 'RECORRENTE',
};

export const sampleWithFullData: ITarefa = {
  id: 6785,
  titulo: 'young',
  numeroDias: 26124,
  diaUtil: true,
  valor: 23550.56,
  notificarCliente: true,
  geraMulta: false,
  exibirEmpresa: false,
  dataLegal: dayjs('2024-08-12T05:38'),
  pontos: 5821,
  tipoTarefa: 'ORDEMSERVICO',
};

export const sampleWithNewData: NewTarefa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
