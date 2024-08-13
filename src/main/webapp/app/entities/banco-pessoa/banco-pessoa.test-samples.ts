import { IBancoPessoa, NewBancoPessoa } from './banco-pessoa.model';

export const sampleWithRequiredData: IBancoPessoa = {
  id: 5126,
  agencia: 'visualize sternly',
  conta: 'sour',
};

export const sampleWithPartialData: IBancoPessoa = {
  id: 31681,
  agencia: 'at mid snow',
  conta: 'tremendous',
  principal: false,
};

export const sampleWithFullData: IBancoPessoa = {
  id: 6403,
  agencia: 'fatally cannibalize',
  conta: 'radicalize masculine against',
  tipoConta: 'CONTASALARIO',
  principal: false,
};

export const sampleWithNewData: NewBancoPessoa = {
  agencia: 'zowie while',
  conta: 'hang',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
