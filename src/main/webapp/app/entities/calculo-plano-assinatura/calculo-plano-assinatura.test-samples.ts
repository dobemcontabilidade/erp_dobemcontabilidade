import { ICalculoPlanoAssinatura, NewCalculoPlanoAssinatura } from './calculo-plano-assinatura.model';

export const sampleWithRequiredData: ICalculoPlanoAssinatura = {
  id: 2078,
};

export const sampleWithPartialData: ICalculoPlanoAssinatura = {
  id: 22584,
  valorRamo: 8411.5,
  valorFuncionarios: 7234.97,
  valorPlanoContaAzulComDesconto: 19602.43,
  valorMensalidade: 1985.48,
};

export const sampleWithFullData: ICalculoPlanoAssinatura = {
  id: 16936,
  codigoAtendimento: 'underneath pharmacopoeia',
  valorEnquadramento: 17293.74,
  valorTributacao: 13317.95,
  valorRamo: 18224.26,
  valorFuncionarios: 1298.08,
  valorSocios: 856.7,
  valorFaturamento: 16729.13,
  valorPlanoContabil: 22118.61,
  valorPlanoContabilComDesconto: 19684.04,
  valorPlanoContaAzulComDesconto: 16352.29,
  valorMensalidade: 125.4,
  valorPeriodo: 22558.85,
  valorAno: 5930.22,
};

export const sampleWithNewData: NewCalculoPlanoAssinatura = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
