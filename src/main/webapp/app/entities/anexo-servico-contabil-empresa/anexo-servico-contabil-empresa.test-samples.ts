import dayjs from 'dayjs/esm';

import { IAnexoServicoContabilEmpresa, NewAnexoServicoContabilEmpresa } from './anexo-servico-contabil-empresa.model';

export const sampleWithRequiredData: IAnexoServicoContabilEmpresa = {
  id: 19221,
};

export const sampleWithPartialData: IAnexoServicoContabilEmpresa = {
  id: 12549,
  link: 'musty because hastily',
  dataHoraUpload: dayjs('2024-08-12T18:48'),
};

export const sampleWithFullData: IAnexoServicoContabilEmpresa = {
  id: 20875,
  link: 'uh-huh cumbersome',
  dataHoraUpload: dayjs('2024-08-12T19:06'),
};

export const sampleWithNewData: NewAnexoServicoContabilEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
