import dayjs from 'dayjs/esm';

import { IServicoContabilAssinaturaEmpresa, NewServicoContabilAssinaturaEmpresa } from './servico-contabil-assinatura-empresa.model';

export const sampleWithRequiredData: IServicoContabilAssinaturaEmpresa = {
  id: 31834,
  dataLegal: dayjs('2024-08-12T17:06'),
  dataAdmin: dayjs('2024-08-12T10:28'),
};

export const sampleWithPartialData: IServicoContabilAssinaturaEmpresa = {
  id: 10530,
  dataLegal: dayjs('2024-08-12T18:53'),
  dataAdmin: dayjs('2024-08-12T09:42'),
};

export const sampleWithFullData: IServicoContabilAssinaturaEmpresa = {
  id: 20846,
  dataLegal: dayjs('2024-08-12T08:39'),
  dataAdmin: dayjs('2024-08-12T22:20'),
};

export const sampleWithNewData: NewServicoContabilAssinaturaEmpresa = {
  dataLegal: dayjs('2024-08-12T11:09'),
  dataAdmin: dayjs('2024-08-12T17:53'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
