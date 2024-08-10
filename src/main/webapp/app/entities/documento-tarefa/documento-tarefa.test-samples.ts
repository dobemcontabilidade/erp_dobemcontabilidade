import { IDocumentoTarefa, NewDocumentoTarefa } from './documento-tarefa.model';

export const sampleWithRequiredData: IDocumentoTarefa = {
  id: 24404,
};

export const sampleWithPartialData: IDocumentoTarefa = {
  id: 21344,
  nome: 'township which zowie',
};

export const sampleWithFullData: IDocumentoTarefa = {
  id: 18588,
  nome: 'ew',
};

export const sampleWithNewData: NewDocumentoTarefa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
