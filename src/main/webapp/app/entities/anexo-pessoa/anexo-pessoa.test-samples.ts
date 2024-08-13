import { IAnexoPessoa, NewAnexoPessoa } from './anexo-pessoa.model';

export const sampleWithRequiredData: IAnexoPessoa = {
  id: 2353,
};

export const sampleWithPartialData: IAnexoPessoa = {
  id: 1102,
  urlArquivo: '../fake-data/blob/hipster.txt',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IAnexoPessoa = {
  id: 31469,
  urlArquivo: '../fake-data/blob/hipster.txt',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAnexoPessoa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
