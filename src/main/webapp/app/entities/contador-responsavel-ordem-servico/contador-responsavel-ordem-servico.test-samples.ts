import dayjs from 'dayjs/esm';

import { IContadorResponsavelOrdemServico, NewContadorResponsavelOrdemServico } from './contador-responsavel-ordem-servico.model';

export const sampleWithRequiredData: IContadorResponsavelOrdemServico = {
  id: 4854,
};

export const sampleWithPartialData: IContadorResponsavelOrdemServico = {
  id: 7930,
  dataAtribuicao: dayjs('2024-08-13T01:55'),
};

export const sampleWithFullData: IContadorResponsavelOrdemServico = {
  id: 28647,
  dataAtribuicao: dayjs('2024-08-13T04:54'),
  dataRevogacao: dayjs('2024-08-13T04:35'),
};

export const sampleWithNewData: NewContadorResponsavelOrdemServico = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
