import dayjs from 'dayjs/esm';

import { ITarefaEmpresa, NewTarefaEmpresa } from './tarefa-empresa.model';

export const sampleWithRequiredData: ITarefaEmpresa = {
  id: 6425,
};

export const sampleWithPartialData: ITarefaEmpresa = {
  id: 11796,
};

export const sampleWithFullData: ITarefaEmpresa = {
  id: 28857,
  dataHora: dayjs('2024-08-09T14:55'),
};

export const sampleWithNewData: NewTarefaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
