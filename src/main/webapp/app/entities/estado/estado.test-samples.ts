import { IEstado, NewEstado } from './estado.model';

export const sampleWithRequiredData: IEstado = {
  id: 22929,
};

export const sampleWithPartialData: IEstado = {
  id: 29678,
  nome: 'hypothermia',
  naturalidade: 'yippee lieutenant',
  sigla: 'ax',
};

export const sampleWithFullData: IEstado = {
  id: 18506,
  nome: 'skeletal',
  naturalidade: 'sick fudge',
  sigla: 're',
};

export const sampleWithNewData: NewEstado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
