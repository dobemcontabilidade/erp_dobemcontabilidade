import { IPais, NewPais } from './pais.model';

export const sampleWithRequiredData: IPais = {
  id: 21203,
};

export const sampleWithPartialData: IPais = {
  id: 22081,
  sigla: 'gadzooks beautifully',
};

export const sampleWithFullData: IPais = {
  id: 24942,
  nome: 'grouse vote',
  nacionalidade: 'meh',
  sigla: 'mammoth besides yippee',
};

export const sampleWithNewData: NewPais = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
