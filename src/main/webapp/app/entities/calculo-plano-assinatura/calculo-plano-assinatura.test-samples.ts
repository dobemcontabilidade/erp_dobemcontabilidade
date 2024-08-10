import { ICalculoPlanoAssinatura, NewCalculoPlanoAssinatura } from './calculo-plano-assinatura.model';

export const sampleWithRequiredData: ICalculoPlanoAssinatura = {
  id: 32751,
};

export const sampleWithPartialData: ICalculoPlanoAssinatura = {
  id: 1065,
  codigoAtendimento: 'boohoo',
  valorRamo: 3721.01,
  valorSocios: 25434.24,
  valorFaturamento: 11261.54,
  valorPlanoContaAzulComDesconto: 18655.72,
  valorMensalidade: 228.77,
};

export const sampleWithFullData: ICalculoPlanoAssinatura = {
  id: 23003,
  codigoAtendimento: 'intercut barring uh-huh',
  valorEnquadramento: 7807.75,
  valorTributacao: 19073.45,
  valorRamo: 13243.92,
  valorFuncionarios: 13854.46,
  valorSocios: 2861.84,
  valorFaturamento: 461.15,
  valorPlanoContabil: 24168.2,
  valorPlanoContabilComDesconto: 27315.36,
  valorPlanoContaAzulComDesconto: 12780.67,
  valorMensalidade: 5142.04,
  valorPeriodo: 17693.37,
  valorAno: 31669.09,
};

export const sampleWithNewData: NewCalculoPlanoAssinatura = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
