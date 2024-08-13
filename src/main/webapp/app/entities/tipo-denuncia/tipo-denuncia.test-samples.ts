import { ITipoDenuncia, NewTipoDenuncia } from './tipo-denuncia.model';

export const sampleWithRequiredData: ITipoDenuncia = {
  id: 17647,
};

export const sampleWithPartialData: ITipoDenuncia = {
  id: 8306,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: ITipoDenuncia = {
  id: 20206,
  tipo: 'cruelly wearily attorney',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewTipoDenuncia = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
