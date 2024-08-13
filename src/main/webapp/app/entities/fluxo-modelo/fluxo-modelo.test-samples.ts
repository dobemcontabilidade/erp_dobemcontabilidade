import { IFluxoModelo, NewFluxoModelo } from './fluxo-modelo.model';

export const sampleWithRequiredData: IFluxoModelo = {
  id: 4406,
};

export const sampleWithPartialData: IFluxoModelo = {
  id: 6573,
};

export const sampleWithFullData: IFluxoModelo = {
  id: 22541,
  nome: 'ready back given',
  descricao: 'zowie kissingly apprehensive',
};

export const sampleWithNewData: NewFluxoModelo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
