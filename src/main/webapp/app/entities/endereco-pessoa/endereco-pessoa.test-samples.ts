import { IEnderecoPessoa, NewEnderecoPessoa } from './endereco-pessoa.model';

export const sampleWithRequiredData: IEnderecoPessoa = {
  id: 32749,
};

export const sampleWithPartialData: IEnderecoPessoa = {
  id: 7655,
  cep: 'voluntarily',
};

export const sampleWithFullData: IEnderecoPessoa = {
  id: 14621,
  logradouro: 'however vegetable nearly',
  numero: 'joshingly',
  complemento: 'proposition only',
  bairro: 'ugh or shame',
  cep: 'hmph whether through',
  principal: true,
};

export const sampleWithNewData: NewEnderecoPessoa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
