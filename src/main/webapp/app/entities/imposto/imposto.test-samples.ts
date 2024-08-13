import { IImposto, NewImposto } from './imposto.model';

export const sampleWithRequiredData: IImposto = {
  id: 16675,
};

export const sampleWithPartialData: IImposto = {
  id: 25964,
  nome: 'glaring',
};

export const sampleWithFullData: IImposto = {
  id: 23096,
  nome: 'variable pfft',
  sigla: 'robust',
  descricao: 'someone joyfully frail',
};

export const sampleWithNewData: NewImposto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
