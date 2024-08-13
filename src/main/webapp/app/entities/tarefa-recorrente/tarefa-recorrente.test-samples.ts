import dayjs from 'dayjs/esm';

import { ITarefaRecorrente, NewTarefaRecorrente } from './tarefa-recorrente.model';

export const sampleWithRequiredData: ITarefaRecorrente = {
  id: 23610,
};

export const sampleWithPartialData: ITarefaRecorrente = {
  id: 7730,
  nome: 'inform duh redden',
  descricao: 'yet',
  notificarCliente: false,
  anoReferencia: 31150,
  recorencia: 'SEMESTRAL',
};

export const sampleWithFullData: ITarefaRecorrente = {
  id: 8407,
  nome: 'once',
  descricao: 'fitting people',
  notificarCliente: true,
  notificarContador: true,
  anoReferencia: 20330,
  dataAdmin: dayjs('2024-08-12T18:31'),
  recorencia: 'ANUAL',
};

export const sampleWithNewData: NewTarefaRecorrente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
