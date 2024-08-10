import { IPerfilContadorAreaContabil, NewPerfilContadorAreaContabil } from './perfil-contador-area-contabil.model';

export const sampleWithRequiredData: IPerfilContadorAreaContabil = {
  id: 8594,
};

export const sampleWithPartialData: IPerfilContadorAreaContabil = {
  id: 6887,
  quantidadeEmpresas: 10413,
};

export const sampleWithFullData: IPerfilContadorAreaContabil = {
  id: 8556,
  quantidadeEmpresas: 8063,
  percentualExperiencia: 27218.63,
};

export const sampleWithNewData: NewPerfilContadorAreaContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
