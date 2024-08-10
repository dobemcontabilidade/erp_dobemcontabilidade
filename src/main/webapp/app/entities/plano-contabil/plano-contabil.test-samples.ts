import { IPlanoContabil, NewPlanoContabil } from './plano-contabil.model';

export const sampleWithRequiredData: IPlanoContabil = {
  id: 28601,
};

export const sampleWithPartialData: IPlanoContabil = {
  id: 24307,
  adicionalSocio: 4227.69,
  sociosIsentos: 1727,
  valorBaseFaturamento: 2209.99,
  situacao: 'ATIVO',
};

export const sampleWithFullData: IPlanoContabil = {
  id: 8338,
  nome: 'while',
  adicionalSocio: 15010.17,
  adicionalFuncionario: 14400.44,
  sociosIsentos: 3762,
  adicionalFaturamento: 8589.05,
  valorBaseFaturamento: 21410.63,
  valorBaseAbertura: 31557.73,
  situacao: 'EXCLUIDO',
};

export const sampleWithNewData: NewPlanoContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
