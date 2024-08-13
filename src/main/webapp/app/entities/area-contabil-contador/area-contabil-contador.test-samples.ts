import { IAreaContabilContador, NewAreaContabilContador } from './area-contabil-contador.model';

export const sampleWithRequiredData: IAreaContabilContador = {
  id: 27047,
};

export const sampleWithPartialData: IAreaContabilContador = {
  id: 22793,
  descricaoExperiencia: 'woot federation',
};

export const sampleWithFullData: IAreaContabilContador = {
  id: 5413,
  percentualExperiencia: 20177.03,
  descricaoExperiencia: 'ordinary',
  ativo: false,
};

export const sampleWithNewData: NewAreaContabilContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
