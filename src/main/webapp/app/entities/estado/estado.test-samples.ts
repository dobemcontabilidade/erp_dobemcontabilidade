import { IEstado, NewEstado } from './estado.model';

export const sampleWithRequiredData: IEstado = {
  id: 17107,
};

export const sampleWithPartialData: IEstado = {
  id: 31798,
  nome: 'yet',
  sigla: 'truant which often',
};

export const sampleWithFullData: IEstado = {
  id: 27472,
  nome: 'gleefully',
  naturalidade: 'dismal once',
  sigla: 'TV following glossy',
};

export const sampleWithNewData: NewEstado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
