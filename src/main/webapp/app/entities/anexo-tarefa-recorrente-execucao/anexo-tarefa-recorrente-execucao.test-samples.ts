import dayjs from 'dayjs/esm';

import { IAnexoTarefaRecorrenteExecucao, NewAnexoTarefaRecorrenteExecucao } from './anexo-tarefa-recorrente-execucao.model';

export const sampleWithRequiredData: IAnexoTarefaRecorrenteExecucao = {
  id: 19973,
};

export const sampleWithPartialData: IAnexoTarefaRecorrenteExecucao = {
  id: 16339,
  descricao: 'where',
};

export const sampleWithFullData: IAnexoTarefaRecorrenteExecucao = {
  id: 29829,
  url: 'https://courageous-drawbridge.net/',
  descricao: 'correctly however',
  dataHoraUpload: dayjs('2024-08-12T11:36'),
};

export const sampleWithNewData: NewAnexoTarefaRecorrenteExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
