import dayjs from 'dayjs/esm';

import { ITarefaEmpresa, NewTarefaEmpresa } from './tarefa-empresa.model';

export const sampleWithRequiredData: ITarefaEmpresa = {
  id: 29056,
};

export const sampleWithPartialData: ITarefaEmpresa = {
  id: 28349,
};

export const sampleWithFullData: ITarefaEmpresa = {
  id: 18148,
  dataHora: dayjs('2024-08-12T05:50'),
};

export const sampleWithNewData: NewTarefaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
