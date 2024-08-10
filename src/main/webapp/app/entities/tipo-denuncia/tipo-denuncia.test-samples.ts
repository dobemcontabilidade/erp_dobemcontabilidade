import { ITipoDenuncia, NewTipoDenuncia } from './tipo-denuncia.model';

export const sampleWithRequiredData: ITipoDenuncia = {
  id: 10598,
};

export const sampleWithPartialData: ITipoDenuncia = {
  id: 16869,
  tipo: 'impala direct agonizing',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ITipoDenuncia = {
  id: 9566,
  tipo: 'kindhearted pace opposite',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewTipoDenuncia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
