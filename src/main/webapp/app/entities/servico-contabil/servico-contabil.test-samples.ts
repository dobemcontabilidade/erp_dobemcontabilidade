import { IServicoContabil, NewServicoContabil } from './servico-contabil.model';

export const sampleWithRequiredData: IServicoContabil = {
  id: 3753,
};

export const sampleWithPartialData: IServicoContabil = {
  id: 19562,
  nome: 'aboard',
};

export const sampleWithFullData: IServicoContabil = {
  id: 29344,
  nome: 'phew yippee',
  valor: 5878.34,
  descricao: 'composition patch',
  diasExecucao: 16592,
  geraMulta: false,
  periodoExecucao: 12176,
  diaLegal: 31720,
  mesLegal: 31881,
  valorRefMulta: 7715.5,
};

export const sampleWithNewData: NewServicoContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
