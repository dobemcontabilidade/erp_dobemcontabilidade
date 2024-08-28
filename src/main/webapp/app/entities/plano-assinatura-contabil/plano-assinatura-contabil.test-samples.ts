import { IPlanoAssinaturaContabil, NewPlanoAssinaturaContabil } from './plano-assinatura-contabil.model';

export const sampleWithRequiredData: IPlanoAssinaturaContabil = {
  id: 17359,
};

export const sampleWithPartialData: IPlanoAssinaturaContabil = {
  id: 15525,
  nome: 'sometimes',
  adicionalSocio: 5705.85,
  adicionalFaturamento: 17379.77,
  valorBaseFaturamento: 16882.21,
};

export const sampleWithFullData: IPlanoAssinaturaContabil = {
  id: 19445,
  nome: 'scurry',
  adicionalSocio: 9243.82,
  adicionalFuncionario: 25786.06,
  sociosIsentos: 19211,
  adicionalFaturamento: 2679.48,
  valorBaseFaturamento: 12566.84,
  valorBaseAbertura: 15843.94,
  situacao: 'EXCLUIDO',
};

export const sampleWithNewData: NewPlanoAssinaturaContabil = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
