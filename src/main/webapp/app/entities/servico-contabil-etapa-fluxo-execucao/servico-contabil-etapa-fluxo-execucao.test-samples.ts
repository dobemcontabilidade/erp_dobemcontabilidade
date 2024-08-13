import { IServicoContabilEtapaFluxoExecucao, NewServicoContabilEtapaFluxoExecucao } from './servico-contabil-etapa-fluxo-execucao.model';

export const sampleWithRequiredData: IServicoContabilEtapaFluxoExecucao = {
  id: 23554,
};

export const sampleWithPartialData: IServicoContabilEtapaFluxoExecucao = {
  id: 25794,
  feito: true,
};

export const sampleWithFullData: IServicoContabilEtapaFluxoExecucao = {
  id: 6730,
  ordem: 17281,
  feito: false,
  prazo: 365,
};

export const sampleWithNewData: NewServicoContabilEtapaFluxoExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
