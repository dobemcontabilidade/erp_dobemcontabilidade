import { IAreaContabilEmpresa, NewAreaContabilEmpresa } from './area-contabil-empresa.model';

export const sampleWithRequiredData: IAreaContabilEmpresa = {
  id: 24746,
};

export const sampleWithPartialData: IAreaContabilEmpresa = {
  id: 2068,
  pontuacao: 7269.81,
  reclamacao: 'toot',
};

export const sampleWithFullData: IAreaContabilEmpresa = {
  id: 5928,
  pontuacao: 29577.79,
  depoimento: 'aha lovely',
  reclamacao: 'extra-small yearningly which',
};

export const sampleWithNewData: NewAreaContabilEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
