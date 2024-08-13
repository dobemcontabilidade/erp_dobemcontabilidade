import { IPlanoContabil, NewPlanoContabil } from './plano-contabil.model';

export const sampleWithRequiredData: IPlanoContabil = {
  id: 9925,
};

export const sampleWithPartialData: IPlanoContabil = {
  id: 847,
  adicionalSocio: 26998.88,
  sociosIsentos: 30451,
  adicionalFaturamento: 9305.95,
  valorBaseAbertura: 23329.4,
};

export const sampleWithFullData: IPlanoContabil = {
  id: 17212,
  nome: 'carefully into spacing',
  adicionalSocio: 21987.5,
  adicionalFuncionario: 2363.33,
  sociosIsentos: 25311,
  adicionalFaturamento: 27814.85,
  valorBaseFaturamento: 31808.54,
  valorBaseAbertura: 29388.12,
  situacao: 'INATIVO',
};

export const sampleWithNewData: NewPlanoContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
