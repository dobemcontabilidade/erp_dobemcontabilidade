import { IAnexoRequeridoTarefaOrdemServico, NewAnexoRequeridoTarefaOrdemServico } from './anexo-requerido-tarefa-ordem-servico.model';

export const sampleWithRequiredData: IAnexoRequeridoTarefaOrdemServico = {
  id: 22150,
};

export const sampleWithPartialData: IAnexoRequeridoTarefaOrdemServico = {
  id: 6953,
};

export const sampleWithFullData: IAnexoRequeridoTarefaOrdemServico = {
  id: 2494,
  obrigatorio: true,
};

export const sampleWithNewData: NewAnexoRequeridoTarefaOrdemServico = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
