import dayjs from 'dayjs/esm';

import { IAreaContabilAssinaturaEmpresa, NewAreaContabilAssinaturaEmpresa } from './area-contabil-assinatura-empresa.model';

export const sampleWithRequiredData: IAreaContabilAssinaturaEmpresa = {
  id: 377,
};

export const sampleWithPartialData: IAreaContabilAssinaturaEmpresa = {
  id: 25079,
  dataAtribuicao: dayjs('2024-08-12T08:27'),
};

export const sampleWithFullData: IAreaContabilAssinaturaEmpresa = {
  id: 31246,
  ativo: false,
  dataAtribuicao: dayjs('2024-08-12T23:32'),
  dataRevogacao: dayjs('2024-08-12T14:44'),
};

export const sampleWithNewData: NewAreaContabilAssinaturaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
