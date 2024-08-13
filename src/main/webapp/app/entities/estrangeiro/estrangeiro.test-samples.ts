import { IEstrangeiro, NewEstrangeiro } from './estrangeiro.model';

export const sampleWithRequiredData: IEstrangeiro = {
  id: 10521,
};

export const sampleWithPartialData: IEstrangeiro = {
  id: 5646,
  dataNaturalizacao: 'undock ick',
};

export const sampleWithFullData: IEstrangeiro = {
  id: 28212,
  dataChegada: 'dear',
  dataNaturalizacao: 'blah ragged quirky',
  casadoComBrasileiro: false,
  filhosComBrasileiro: true,
  checked: true,
};

export const sampleWithNewData: NewEstrangeiro = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
