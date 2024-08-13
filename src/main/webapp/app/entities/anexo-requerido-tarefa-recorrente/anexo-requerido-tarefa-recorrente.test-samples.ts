import { IAnexoRequeridoTarefaRecorrente, NewAnexoRequeridoTarefaRecorrente } from './anexo-requerido-tarefa-recorrente.model';

export const sampleWithRequiredData: IAnexoRequeridoTarefaRecorrente = {
  id: 1175,
};

export const sampleWithPartialData: IAnexoRequeridoTarefaRecorrente = {
  id: 24457,
  obrigatorio: true,
};

export const sampleWithFullData: IAnexoRequeridoTarefaRecorrente = {
  id: 3656,
  obrigatorio: true,
};

export const sampleWithNewData: NewAnexoRequeridoTarefaRecorrente = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
