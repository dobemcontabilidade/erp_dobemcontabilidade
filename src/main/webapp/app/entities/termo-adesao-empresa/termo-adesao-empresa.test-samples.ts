import dayjs from 'dayjs/esm';

import { ITermoAdesaoEmpresa, NewTermoAdesaoEmpresa } from './termo-adesao-empresa.model';

export const sampleWithRequiredData: ITermoAdesaoEmpresa = {
  id: 12758,
};

export const sampleWithPartialData: ITermoAdesaoEmpresa = {
  id: 30973,
  dataAdesao: dayjs('2024-08-12T07:07'),
  checked: false,
  urlDoc: 'heavenly',
};

export const sampleWithFullData: ITermoAdesaoEmpresa = {
  id: 19619,
  dataAdesao: dayjs('2024-08-12T09:07'),
  checked: true,
  urlDoc: 'wherever',
};

export const sampleWithNewData: NewTermoAdesaoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
