import { IContador, NewContador } from './contador.model';

export const sampleWithRequiredData: IContador = {
  id: 5337,
};

export const sampleWithPartialData: IContador = {
  id: 2906,
  limiteEmpresas: 14361,
  limiteDepartamentos: 7815,
  situacaoContador: 'INATIVO',
};

export const sampleWithFullData: IContador = {
  id: 2987,
  crc: 'beautify',
  limiteEmpresas: 12668,
  limiteDepartamentos: 15871,
  limiteFaturamento: 18215.35,
  situacaoContador: 'INAPTO',
};

export const sampleWithNewData: NewContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
