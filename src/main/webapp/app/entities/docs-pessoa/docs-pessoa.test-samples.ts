import { IDocsPessoa, NewDocsPessoa } from './docs-pessoa.model';

export const sampleWithRequiredData: IDocsPessoa = {
  id: 22527,
  urlArquivo: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IDocsPessoa = {
  id: 5852,
  urlArquivo: '../fake-data/blob/hipster.txt',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDocsPessoa = {
  id: 4082,
  urlArquivo: '../fake-data/blob/hipster.txt',
  tipo: 'illiterate',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDocsPessoa = {
  urlArquivo: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
