import { IDescontoPlanoContaAzul, NewDescontoPlanoContaAzul } from './desconto-plano-conta-azul.model';

export const sampleWithRequiredData: IDescontoPlanoContaAzul = {
  id: 4484,
};

export const sampleWithPartialData: IDescontoPlanoContaAzul = {
  id: 3478,
};

export const sampleWithFullData: IDescontoPlanoContaAzul = {
  id: 1860,
  percentual: 24325.42,
};

export const sampleWithNewData: NewDescontoPlanoContaAzul = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
