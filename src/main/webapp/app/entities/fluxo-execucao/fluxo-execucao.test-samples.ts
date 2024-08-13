import { IFluxoExecucao, NewFluxoExecucao } from './fluxo-execucao.model';

export const sampleWithRequiredData: IFluxoExecucao = {
  id: 10738,
};

export const sampleWithPartialData: IFluxoExecucao = {
  id: 13497,
};

export const sampleWithFullData: IFluxoExecucao = {
  id: 13530,
  nome: 'resemblance scissors unaccountably',
  descricao: 'lap carefully of',
};

export const sampleWithNewData: NewFluxoExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
