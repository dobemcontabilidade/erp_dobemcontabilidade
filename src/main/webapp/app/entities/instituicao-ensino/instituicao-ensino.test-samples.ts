import { IInstituicaoEnsino, NewInstituicaoEnsino } from './instituicao-ensino.model';

export const sampleWithRequiredData: IInstituicaoEnsino = {
  id: 14202,
  nome: 'pish',
};

export const sampleWithPartialData: IInstituicaoEnsino = {
  id: 3006,
  nome: 'mysteriously',
  logradouro: 'per',
  complemento: 'lively',
  bairro: 'grim despise',
  cep: 'merrily',
  principal: true,
};

export const sampleWithFullData: IInstituicaoEnsino = {
  id: 4988,
  nome: 'joyfully',
  cnpj: 'squid',
  logradouro: 'likewise moor boohoo',
  numero: 'ick s',
  complemento: 'after sandbar',
  bairro: 'requirement yowza gawp',
  cep: 'riddle co',
  principal: true,
};

export const sampleWithNewData: NewInstituicaoEnsino = {
  nome: 'the where heavenly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
