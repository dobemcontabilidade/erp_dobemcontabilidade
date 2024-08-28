import { IPais, NewPais } from './pais.model';

export const sampleWithRequiredData: IPais = {
  id: 27641,
};

export const sampleWithPartialData: IPais = {
  id: 2166,
  nome: 'yellowish absent avenge',
};

export const sampleWithFullData: IPais = {
  id: 19053,
  nome: 'backtrack astride that',
  nacionalidade: 'state than',
  sigla: 'briefly aha',
};

export const sampleWithNewData: NewPais = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
