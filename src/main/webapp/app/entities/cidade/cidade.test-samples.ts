import { ICidade, NewCidade } from './cidade.model';

export const sampleWithRequiredData: ICidade = {
  id: 28668,
  nome: 'delight fiercely several',
};

export const sampleWithPartialData: ICidade = {
  id: 25011,
  nome: 'ouch anenst',
};

export const sampleWithFullData: ICidade = {
  id: 26813,
  nome: 'wearily optimistically',
  contratacao: true,
  abertura: true,
};

export const sampleWithNewData: NewCidade = {
  nome: 'reskill bravely kowtow',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
