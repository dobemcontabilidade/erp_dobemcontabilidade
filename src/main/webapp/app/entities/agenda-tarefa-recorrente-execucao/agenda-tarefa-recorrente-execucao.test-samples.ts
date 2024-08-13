import dayjs from 'dayjs/esm';

import { IAgendaTarefaRecorrenteExecucao, NewAgendaTarefaRecorrenteExecucao } from './agenda-tarefa-recorrente-execucao.model';

export const sampleWithRequiredData: IAgendaTarefaRecorrenteExecucao = {
  id: 17548,
};

export const sampleWithPartialData: IAgendaTarefaRecorrenteExecucao = {
  id: 20446,
  ativo: true,
  horaInicio: dayjs('2024-08-13T01:09'),
};

export const sampleWithFullData: IAgendaTarefaRecorrenteExecucao = {
  id: 13871,
  ativo: true,
  horaInicio: dayjs('2024-08-12T12:48'),
  horaFim: dayjs('2024-08-12T20:38'),
  diaInteiro: true,
  comentario: 'square until anenst',
};

export const sampleWithNewData: NewAgendaTarefaRecorrenteExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
