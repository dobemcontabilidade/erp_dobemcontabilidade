import { IDescontoPlanoContaAzul, NewDescontoPlanoContaAzul } from './desconto-plano-conta-azul.model';

export const sampleWithRequiredData: IDescontoPlanoContaAzul = {
  id: 21375,
};

export const sampleWithPartialData: IDescontoPlanoContaAzul = {
  id: 27064,
};

export const sampleWithFullData: IDescontoPlanoContaAzul = {
  id: 27619,
  percentual: 5801.57,
};

export const sampleWithNewData: NewDescontoPlanoContaAzul = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
