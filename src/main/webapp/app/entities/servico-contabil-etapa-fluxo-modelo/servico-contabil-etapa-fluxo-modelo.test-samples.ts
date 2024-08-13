import { IServicoContabilEtapaFluxoModelo, NewServicoContabilEtapaFluxoModelo } from './servico-contabil-etapa-fluxo-modelo.model';

export const sampleWithRequiredData: IServicoContabilEtapaFluxoModelo = {
  id: 26836,
};

export const sampleWithPartialData: IServicoContabilEtapaFluxoModelo = {
  id: 12903,
  prazo: 11860,
};

export const sampleWithFullData: IServicoContabilEtapaFluxoModelo = {
  id: 31394,
  ordem: 9513,
  prazo: 29157,
};

export const sampleWithNewData: NewServicoContabilEtapaFluxoModelo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
