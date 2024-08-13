import dayjs from 'dayjs/esm';

import { IAnexoOrdemServicoExecucao, NewAnexoOrdemServicoExecucao } from './anexo-ordem-servico-execucao.model';

export const sampleWithRequiredData: IAnexoOrdemServicoExecucao = {
  id: 8187,
};

export const sampleWithPartialData: IAnexoOrdemServicoExecucao = {
  id: 4229,
  dataHoraUpload: dayjs('2024-08-12T17:37'),
};

export const sampleWithFullData: IAnexoOrdemServicoExecucao = {
  id: 1224,
  url: 'https://oval-return.com',
  descricao: 'meaty',
  dataHoraUpload: dayjs('2024-08-12T17:48'),
};

export const sampleWithNewData: NewAnexoOrdemServicoExecucao = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
