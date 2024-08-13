import dayjs from 'dayjs/esm';

import { ITarefaOrdemServico, NewTarefaOrdemServico } from './tarefa-ordem-servico.model';

export const sampleWithRequiredData: ITarefaOrdemServico = {
  id: 2916,
};

export const sampleWithPartialData: ITarefaOrdemServico = {
  id: 32674,
  descricao: 'instead ugh weakly',
  notificarContador: true,
};

export const sampleWithFullData: ITarefaOrdemServico = {
  id: 23025,
  nome: 'gosh uh-huh',
  descricao: 'idolise woot overdue',
  notificarCliente: false,
  notificarContador: false,
  anoReferencia: 1041,
  dataAdmin: dayjs('2024-08-12T17:22'),
};

export const sampleWithNewData: NewTarefaOrdemServico = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
