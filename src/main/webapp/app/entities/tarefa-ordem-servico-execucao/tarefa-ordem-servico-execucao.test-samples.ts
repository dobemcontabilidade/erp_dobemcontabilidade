import dayjs from 'dayjs/esm';

import { ITarefaOrdemServicoExecucao, NewTarefaOrdemServicoExecucao } from './tarefa-ordem-servico-execucao.model';

export const sampleWithRequiredData: ITarefaOrdemServicoExecucao = {
  id: 9077,
};

export const sampleWithPartialData: ITarefaOrdemServicoExecucao = {
  id: 12878,
  descricao: 'droopy',
  ordem: 259,
  notificarCliente: false,
  mes: 'AGOSTO',
  situacaoTarefa: 'PENDENTE',
};

export const sampleWithFullData: ITarefaOrdemServicoExecucao = {
  id: 26574,
  nome: 'commission',
  descricao: 'kendo whole upliftingly',
  ordem: 11026,
  dataEntrega: dayjs('2024-08-12T09:18'),
  dataAgendada: dayjs('2024-08-12T10:51'),
  concluida: true,
  notificarCliente: false,
  mes: 'MARCO',
  situacaoTarefa: 'EMEXECUCAO',
};

export const sampleWithNewData: NewTarefaOrdemServicoExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
