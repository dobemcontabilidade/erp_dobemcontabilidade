import { ISubclasseCnae, NewSubclasseCnae } from './subclasse-cnae.model';

export const sampleWithRequiredData: ISubclasseCnae = {
  id: 11463,
  codigo: 'partially until',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ISubclasseCnae = {
  id: 8428,
  codigo: 'when indeed',
  descricao: '../fake-data/blob/hipster.txt',
  atendidoFreemium: true,
  categoria: 'yahoo weight incidentally',
};

export const sampleWithFullData: ISubclasseCnae = {
  id: 6417,
  codigo: 'distinguish yum',
  descricao: '../fake-data/blob/hipster.txt',
  anexo: 32541,
  atendidoFreemium: false,
  atendido: true,
  optanteSimples: true,
  aceitaMEI: true,
  categoria: 'aw interestingly promptly',
};

export const sampleWithNewData: NewSubclasseCnae = {
  codigo: 'concerning grac',
  descricao: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
