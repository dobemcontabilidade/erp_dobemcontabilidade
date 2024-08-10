import { IRamo, NewRamo } from './ramo.model';

export const sampleWithRequiredData: IRamo = {
  id: 31427,
};

export const sampleWithPartialData: IRamo = {
  id: 18810,
};

export const sampleWithFullData: IRamo = {
  id: 1079,
  nome: 'eek thunderbolt yippee',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewRamo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
