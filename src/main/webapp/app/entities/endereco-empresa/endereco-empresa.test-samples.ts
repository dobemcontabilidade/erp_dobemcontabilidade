import { IEnderecoEmpresa, NewEnderecoEmpresa } from './endereco-empresa.model';

export const sampleWithRequiredData: IEnderecoEmpresa = {
  id: 32676,
};

export const sampleWithPartialData: IEnderecoEmpresa = {
  id: 13471,
  complemento: 'stalk geez moai',
  bairro: 'pleased',
  principal: false,
};

export const sampleWithFullData: IEnderecoEmpresa = {
  id: 11949,
  logradouro: 'now wherever',
  numero: 'likew',
  complemento: 'seldom',
  bairro: 'thaw smoothly',
  cep: 'candid no',
  principal: true,
  filial: true,
  enderecoFiscal: false,
};

export const sampleWithNewData: NewEnderecoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
