import { IPais, NewPais } from './pais.model';

export const sampleWithRequiredData: IPais = {
  id: 5861,
};

export const sampleWithPartialData: IPais = {
  id: 30449,
};

export const sampleWithFullData: IPais = {
  id: 13769,
  nome: 'up',
  nacionalidade: 'remnant aside fooey',
  sigla: 'abnormally',
};

export const sampleWithNewData: NewPais = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
