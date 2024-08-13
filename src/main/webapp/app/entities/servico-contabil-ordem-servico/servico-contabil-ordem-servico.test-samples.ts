import dayjs from 'dayjs/esm';

import { IServicoContabilOrdemServico, NewServicoContabilOrdemServico } from './servico-contabil-ordem-servico.model';

export const sampleWithRequiredData: IServicoContabilOrdemServico = {
  id: 15838,
};

export const sampleWithPartialData: IServicoContabilOrdemServico = {
  id: 22598,
};

export const sampleWithFullData: IServicoContabilOrdemServico = {
  id: 24519,
  dataAdmin: dayjs('2024-08-12T11:09'),
  dataLegal: dayjs('2024-08-12T12:09'),
};

export const sampleWithNewData: NewServicoContabilOrdemServico = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
