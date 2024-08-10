import { IContador, NewContador } from './contador.model';

export const sampleWithRequiredData: IContador = {
  id: 15647,
  crc: 'punctual affectionate',
};

export const sampleWithPartialData: IContador = {
  id: 30495,
  nome: 'monthly fun via',
  crc: 'wee',
  limiteFaturamento: 24592.14,
};

export const sampleWithFullData: IContador = {
  id: 30086,
  nome: 'openly rating mileage',
  crc: 'waveform slushy duh',
  limiteEmpresas: 18642,
  limiteAreaContabils: 14700,
  limiteFaturamento: 7829.13,
  limiteDepartamentos: 21546,
};

export const sampleWithNewData: NewContador = {
  crc: 'rankle safe',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
