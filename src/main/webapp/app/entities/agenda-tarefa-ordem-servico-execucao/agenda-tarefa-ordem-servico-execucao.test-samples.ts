import dayjs from 'dayjs/esm';

import { IAgendaTarefaOrdemServicoExecucao, NewAgendaTarefaOrdemServicoExecucao } from './agenda-tarefa-ordem-servico-execucao.model';

export const sampleWithRequiredData: IAgendaTarefaOrdemServicoExecucao = {
  id: 7564,
};

export const sampleWithPartialData: IAgendaTarefaOrdemServicoExecucao = {
  id: 14661,
  horaInicio: dayjs('2024-08-12T10:27'),
  diaInteiro: true,
};

export const sampleWithFullData: IAgendaTarefaOrdemServicoExecucao = {
  id: 3899,
  horaInicio: dayjs('2024-08-13T02:19'),
  horaFim: dayjs('2024-08-12T21:07'),
  diaInteiro: false,
  ativo: false,
};

export const sampleWithNewData: NewAgendaTarefaOrdemServicoExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
