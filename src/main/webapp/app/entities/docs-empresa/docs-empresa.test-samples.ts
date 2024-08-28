import dayjs from 'dayjs/esm';

import { IDocsEmpresa, NewDocsEmpresa } from './docs-empresa.model';

export const sampleWithRequiredData: IDocsEmpresa = {
  id: 2370,
};

export const sampleWithPartialData: IDocsEmpresa = {
  id: 11842,
  orgaoEmissor: 'consciousness like potable',
};

export const sampleWithFullData: IDocsEmpresa = {
  id: 22693,
  documento: 'boohoo',
  descricao: '../fake-data/blob/hipster.txt',
  url: '../fake-data/blob/hipster.txt',
  dataEmissao: dayjs('2024-08-27T21:02'),
  dataEncerramento: dayjs('2024-08-27T21:56'),
  orgaoEmissor: 'um',
};

export const sampleWithNewData: NewDocsEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
