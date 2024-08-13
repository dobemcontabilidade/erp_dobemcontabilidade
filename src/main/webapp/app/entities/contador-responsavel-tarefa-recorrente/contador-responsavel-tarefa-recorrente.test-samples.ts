import dayjs from 'dayjs/esm';

import {
  IContadorResponsavelTarefaRecorrente,
  NewContadorResponsavelTarefaRecorrente,
} from './contador-responsavel-tarefa-recorrente.model';

export const sampleWithRequiredData: IContadorResponsavelTarefaRecorrente = {
  id: 8966,
};

export const sampleWithPartialData: IContadorResponsavelTarefaRecorrente = {
  id: 2652,
  dataRevogacao: dayjs('2024-08-12T18:42'),
  concluida: false,
};

export const sampleWithFullData: IContadorResponsavelTarefaRecorrente = {
  id: 25810,
  dataAtribuicao: dayjs('2024-08-12T19:49'),
  dataRevogacao: dayjs('2024-08-12T14:21'),
  concluida: false,
};

export const sampleWithNewData: NewContadorResponsavelTarefaRecorrente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
