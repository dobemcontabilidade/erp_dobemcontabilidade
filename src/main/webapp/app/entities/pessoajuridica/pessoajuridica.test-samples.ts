import { IPessoajuridica, NewPessoajuridica } from './pessoajuridica.model';

export const sampleWithRequiredData: IPessoajuridica = {
  id: 23460,
  razaoSocial: 'eek',
  nomeFantasia: 'unpleasant',
};

export const sampleWithPartialData: IPessoajuridica = {
  id: 24957,
  razaoSocial: 'wrongly',
  nomeFantasia: 'ah outside',
  cnpj: 'or hare',
};

export const sampleWithFullData: IPessoajuridica = {
  id: 28301,
  razaoSocial: 'um',
  nomeFantasia: 'within overbalance',
  cnpj: 'mmm',
};

export const sampleWithNewData: NewPessoajuridica = {
  razaoSocial: 'interlink supportive',
  nomeFantasia: 'soulful times since',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
