import dayjs from 'dayjs/esm';

import { ITarefaEmpresaModelo, NewTarefaEmpresaModelo } from './tarefa-empresa-modelo.model';

export const sampleWithRequiredData: ITarefaEmpresaModelo = {
  id: 5908,
};

export const sampleWithPartialData: ITarefaEmpresaModelo = {
  id: 18366,
};

export const sampleWithFullData: ITarefaEmpresaModelo = {
  id: 12541,
  dataAdmin: dayjs('2024-08-12T09:05'),
  dataLegal: dayjs('2024-08-12T19:50'),
};

export const sampleWithNewData: NewTarefaEmpresaModelo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
