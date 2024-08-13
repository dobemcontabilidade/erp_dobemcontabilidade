import { IModulo, NewModulo } from './modulo.model';

export const sampleWithRequiredData: IModulo = {
  id: 4545,
};

export const sampleWithPartialData: IModulo = {
  id: 26660,
};

export const sampleWithFullData: IModulo = {
  id: 32065,
  nome: 'before desensitise',
  descricao: 'vice',
};

export const sampleWithNewData: NewModulo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
