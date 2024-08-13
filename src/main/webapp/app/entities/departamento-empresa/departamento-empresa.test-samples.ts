import { IDepartamentoEmpresa, NewDepartamentoEmpresa } from './departamento-empresa.model';

export const sampleWithRequiredData: IDepartamentoEmpresa = {
  id: 21555,
};

export const sampleWithPartialData: IDepartamentoEmpresa = {
  id: 21871,
};

export const sampleWithFullData: IDepartamentoEmpresa = {
  id: 20132,
  pontuacao: 16133.24,
  depoimento: 'finally',
  reclamacao: 'steeple',
};

export const sampleWithNewData: NewDepartamentoEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
