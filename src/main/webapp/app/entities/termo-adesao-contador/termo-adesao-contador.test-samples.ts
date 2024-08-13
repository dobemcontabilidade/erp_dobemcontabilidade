import dayjs from 'dayjs/esm';

import { ITermoAdesaoContador, NewTermoAdesaoContador } from './termo-adesao-contador.model';

export const sampleWithRequiredData: ITermoAdesaoContador = {
  id: 27292,
};

export const sampleWithPartialData: ITermoAdesaoContador = {
  id: 2322,
  dataAdesao: dayjs('2024-08-12T18:03'),
};

export const sampleWithFullData: ITermoAdesaoContador = {
  id: 14418,
  dataAdesao: dayjs('2024-08-12T10:23'),
  checked: false,
  urlDoc: 'pelt why',
};

export const sampleWithNewData: NewTermoAdesaoContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
