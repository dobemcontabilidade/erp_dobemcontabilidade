import { IDepartamentoEmpresa, NewDepartamentoEmpresa } from './departamento-empresa.model';

export const sampleWithRequiredData: IDepartamentoEmpresa = {
  id: 9277,
};

export const sampleWithPartialData: IDepartamentoEmpresa = {
  id: 7298,
  pontuacao: 14157.66,
  depoimento: 'neglected genuine',
  reclamacao: 'ambitious provided',
};

export const sampleWithFullData: IDepartamentoEmpresa = {
  id: 15679,
  pontuacao: 16106.61,
  depoimento: 'underneath',
  reclamacao: 'aha',
};

export const sampleWithNewData: NewDepartamentoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
