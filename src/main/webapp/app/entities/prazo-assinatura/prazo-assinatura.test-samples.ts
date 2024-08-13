import { IPrazoAssinatura, NewPrazoAssinatura } from './prazo-assinatura.model';

export const sampleWithRequiredData: IPrazoAssinatura = {
  id: 175,
};

export const sampleWithPartialData: IPrazoAssinatura = {
  id: 29411,
  prazo: 'ack',
  meses: 17777,
};

export const sampleWithFullData: IPrazoAssinatura = {
  id: 8871,
  prazo: 'down cilantro ha',
  meses: 30648,
};

export const sampleWithNewData: NewPrazoAssinatura = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
