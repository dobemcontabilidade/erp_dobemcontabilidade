import { IContador, NewContador } from './contador.model';

export const sampleWithRequiredData: IContador = {
  id: 32377,
  crc: 'vengeful toast',
};

export const sampleWithPartialData: IContador = {
  id: 22034,
  nome: 'faraway ah',
  crc: 'overwhelm',
};

export const sampleWithFullData: IContador = {
  id: 22772,
  nome: 'pretty which',
  crc: 'herbs',
  limiteEmpresas: 25918,
  limiteAreaContabils: 6669,
  limiteFaturamento: 27509.73,
  limiteDepartamentos: 18373,
};

export const sampleWithNewData: NewContador = {
  crc: 'waveform slushy duh',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
