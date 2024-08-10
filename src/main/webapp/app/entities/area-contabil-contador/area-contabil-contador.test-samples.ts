import { IAreaContabilContador, NewAreaContabilContador } from './area-contabil-contador.model';

export const sampleWithRequiredData: IAreaContabilContador = {
  id: 25685,
};

export const sampleWithPartialData: IAreaContabilContador = {
  id: 20154,
  pontuacaoAvaliacao: 5729.38,
};

export const sampleWithFullData: IAreaContabilContador = {
  id: 2676,
  percentualExperiencia: 8195.21,
  descricaoExperiencia: 'drat meh',
  pontuacaoEntrevista: 31982.59,
  pontuacaoAvaliacao: 24531.84,
};

export const sampleWithNewData: NewAreaContabilContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
