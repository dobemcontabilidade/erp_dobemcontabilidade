import { IEtapaFluxoExecucao, NewEtapaFluxoExecucao } from './etapa-fluxo-execucao.model';

export const sampleWithRequiredData: IEtapaFluxoExecucao = {
  id: 12575,
};

export const sampleWithPartialData: IEtapaFluxoExecucao = {
  id: 19928,
  nome: 'angel dearly spirit',
  ordem: 27402,
};

export const sampleWithFullData: IEtapaFluxoExecucao = {
  id: 8039,
  nome: 'exactly ambiguity',
  descricao: 'ha',
  feito: false,
  ordem: 28589,
  agendada: true,
};

export const sampleWithNewData: NewEtapaFluxoExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
