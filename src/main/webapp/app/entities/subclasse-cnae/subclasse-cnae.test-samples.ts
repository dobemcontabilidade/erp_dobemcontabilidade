import { ISubclasseCnae, NewSubclasseCnae } from './subclasse-cnae.model';

export const sampleWithRequiredData: ISubclasseCnae = {
  id: 15391,
  codigo: 'immediately',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ISubclasseCnae = {
  id: 159,
  codigo: 'aboard knowledg',
  descricao: '../fake-data/blob/hipster.txt',
  anexo: 11391,
  atendidoFreemium: false,
  optanteSimples: true,
  aceitaMEI: false,
};

export const sampleWithFullData: ISubclasseCnae = {
  id: 1858,
  codigo: 'nor blah',
  descricao: '../fake-data/blob/hipster.txt',
  anexo: 26102,
  atendidoFreemium: true,
  atendido: false,
  optanteSimples: true,
  aceitaMEI: false,
  categoria: 'scar near unwilling',
};

export const sampleWithNewData: NewSubclasseCnae = {
  codigo: 'letter cobble',
  descricao: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
