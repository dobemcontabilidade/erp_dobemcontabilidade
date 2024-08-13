import { ITelefone, NewTelefone } from './telefone.model';

export const sampleWithRequiredData: ITelefone = {
  id: 20679,
  codigoArea: 'reg',
  telefone: 'down',
};

export const sampleWithPartialData: ITelefone = {
  id: 12530,
  codigoArea: 'phe',
  telefone: 'though',
};

export const sampleWithFullData: ITelefone = {
  id: 5092,
  codigoArea: 'rel',
  telefone: 'pout arch-rival',
  principal: false,
  tipoTelefone: 'RESIDENCIAL',
};

export const sampleWithNewData: NewTelefone = {
  codigoArea: 'hmp',
  telefone: 'clerk',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
