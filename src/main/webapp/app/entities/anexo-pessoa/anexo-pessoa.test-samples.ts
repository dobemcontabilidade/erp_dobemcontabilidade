import { IAnexoPessoa, NewAnexoPessoa } from './anexo-pessoa.model';

export const sampleWithRequiredData: IAnexoPessoa = {
  id: 14572,
  urlArquivo: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IAnexoPessoa = {
  id: 14250,
  urlArquivo: '../fake-data/blob/hipster.txt',
  tipo: 'hm',
};

export const sampleWithFullData: IAnexoPessoa = {
  id: 8320,
  urlArquivo: '../fake-data/blob/hipster.txt',
  tipo: 'behind queasily',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewAnexoPessoa = {
  urlArquivo: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
