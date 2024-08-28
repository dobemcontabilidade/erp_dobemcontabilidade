import { IEnderecoEmpresa, NewEnderecoEmpresa } from './endereco-empresa.model';

export const sampleWithRequiredData: IEnderecoEmpresa = {
  id: 32077,
};

export const sampleWithPartialData: IEnderecoEmpresa = {
  id: 17289,
  logradouro: 'closely beneath kindly',
  numero: 'gah positively which',
  complemento: 'if oh',
  bairro: 'onto',
  cep: 'gosh newsprint',
  principal: true,
  filial: false,
  enderecoFiscal: true,
};

export const sampleWithFullData: IEnderecoEmpresa = {
  id: 27997,
  logradouro: 'um',
  numero: 'plump',
  complemento: 'boohoo',
  bairro: 'pelt borrowing',
  cep: 'dry',
  principal: false,
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
