import { ISubTarefaRecorrente, NewSubTarefaRecorrente } from './sub-tarefa-recorrente.model';

export const sampleWithRequiredData: ISubTarefaRecorrente = {
  id: 13010,
};

export const sampleWithPartialData: ISubTarefaRecorrente = {
  id: 21667,
  descricao: 'loving utterly',
  ordem: 14043,
};

export const sampleWithFullData: ISubTarefaRecorrente = {
  id: 21103,
  nome: 'junior',
  descricao: 'than winged interview',
  ordem: 11710,
  concluida: true,
};

export const sampleWithNewData: NewSubTarefaRecorrente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
