import { IEnderecoEmpresa, NewEnderecoEmpresa } from './endereco-empresa.model';

export const sampleWithRequiredData: IEnderecoEmpresa = {
  id: 3944,
};

export const sampleWithPartialData: IEnderecoEmpresa = {
  id: 23829,
  bairro: 'modulo',
  cep: 'unequaled among',
  principal: false,
  enderecoFiscal: false,
};

export const sampleWithFullData: IEnderecoEmpresa = {
  id: 11446,
  logradouro: 'energetically ugh',
  numero: 'lazily',
  complemento: 'via an',
  bairro: 'duh',
  cep: 'yum unless whoa',
  principal: true,
  filial: true,
  enderecoFiscal: true,
};

export const sampleWithNewData: NewEnderecoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
