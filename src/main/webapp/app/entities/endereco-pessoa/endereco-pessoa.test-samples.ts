import { IEnderecoPessoa, NewEnderecoPessoa } from './endereco-pessoa.model';

export const sampleWithRequiredData: IEnderecoPessoa = {
  id: 14259,
};

export const sampleWithPartialData: IEnderecoPessoa = {
  id: 13774,
  cep: 'upon vast',
};

export const sampleWithFullData: IEnderecoPessoa = {
  id: 16443,
  logradouro: 'below futon gratefully',
  numero: 'probable after',
  complemento: 'past soulful hopelessly',
  bairro: 'toward those',
  cep: 'whereas',
  principal: false,
};

export const sampleWithNewData: NewEnderecoPessoa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
