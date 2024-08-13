import dayjs from 'dayjs/esm';

import { ITarefaRecorrenteExecucao, NewTarefaRecorrenteExecucao } from './tarefa-recorrente-execucao.model';

export const sampleWithRequiredData: ITarefaRecorrenteExecucao = {
  id: 29165,
};

export const sampleWithPartialData: ITarefaRecorrenteExecucao = {
  id: 12513,
  nome: 'that sire abdomen',
  dataEntrega: dayjs('2024-08-12T09:15'),
  mes: 'ABRIL',
  situacaoTarefa: 'DELETADA',
};

export const sampleWithFullData: ITarefaRecorrenteExecucao = {
  id: 30943,
  nome: 'peddle whenever',
  descricao: 'whose',
  dataEntrega: dayjs('2024-08-12T08:28'),
  dataAgendada: dayjs('2024-08-13T01:14'),
  ordem: 14036,
  concluida: true,
  mes: 'JUNHO',
  situacaoTarefa: 'EMEXECUCAO',
};

export const sampleWithNewData: NewTarefaRecorrenteExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
