import dayjs from 'dayjs/esm';

import { ITermoAdesaoContador, NewTermoAdesaoContador } from './termo-adesao-contador.model';

export const sampleWithRequiredData: ITermoAdesaoContador = {
  id: 25175,
};

export const sampleWithPartialData: ITermoAdesaoContador = {
  id: 31545,
};

export const sampleWithFullData: ITermoAdesaoContador = {
  id: 10926,
  dataAdesao: dayjs('2024-08-09T16:48'),
};

export const sampleWithNewData: NewTermoAdesaoContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
