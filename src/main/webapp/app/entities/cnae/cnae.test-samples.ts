import { ICnae, NewCnae } from './cnae.model';

export const sampleWithRequiredData: ICnae = {
  id: 18549,
  codigo: 'when flatline',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: ICnae = {
  id: 8833,
  codigo: 'by accent since',
  descricao: '../fake-data/blob/hipster.txt',
  optanteSimples: false,
};

export const sampleWithFullData: ICnae = {
  id: 15460,
  codigo: 'consensus beneath',
  descricao: '../fake-data/blob/hipster.txt',
  anexo: 31451,
  atendidoFreemium: false,
  atendido: false,
  optanteSimples: false,
  categoria: 'aromatic garrote but',
};

export const sampleWithNewData: NewCnae = {
  codigo: 'yuck across tune',
  descricao: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
