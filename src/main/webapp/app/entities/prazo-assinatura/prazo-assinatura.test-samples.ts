import { IPrazoAssinatura, NewPrazoAssinatura } from './prazo-assinatura.model';

export const sampleWithRequiredData: IPrazoAssinatura = {
  id: 24693,
};

export const sampleWithPartialData: IPrazoAssinatura = {
  id: 20353,
};

export const sampleWithFullData: IPrazoAssinatura = {
  id: 24971,
  prazo: 'so',
  meses: 22723,
};

export const sampleWithNewData: NewPrazoAssinatura = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
