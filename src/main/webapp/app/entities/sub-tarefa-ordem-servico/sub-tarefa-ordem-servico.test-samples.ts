import { ISubTarefaOrdemServico, NewSubTarefaOrdemServico } from './sub-tarefa-ordem-servico.model';

export const sampleWithRequiredData: ISubTarefaOrdemServico = {
  id: 7558,
};

export const sampleWithPartialData: ISubTarefaOrdemServico = {
  id: 8266,
  nome: 'amidst',
  descricao: 'like pish',
  ordem: 28531,
};

export const sampleWithFullData: ISubTarefaOrdemServico = {
  id: 18174,
  nome: 'interview collapse abaft',
  descricao: 'raise sudden concerning',
  ordem: 31354,
  concluida: false,
};

export const sampleWithNewData: NewSubTarefaOrdemServico = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
