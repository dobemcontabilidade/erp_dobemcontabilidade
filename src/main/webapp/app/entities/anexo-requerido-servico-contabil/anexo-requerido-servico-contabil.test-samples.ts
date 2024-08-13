import { IAnexoRequeridoServicoContabil, NewAnexoRequeridoServicoContabil } from './anexo-requerido-servico-contabil.model';

export const sampleWithRequiredData: IAnexoRequeridoServicoContabil = {
  id: 19187,
};

export const sampleWithPartialData: IAnexoRequeridoServicoContabil = {
  id: 12248,
};

export const sampleWithFullData: IAnexoRequeridoServicoContabil = {
  id: 15958,
  obrigatorio: false,
};

export const sampleWithNewData: NewAnexoRequeridoServicoContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
