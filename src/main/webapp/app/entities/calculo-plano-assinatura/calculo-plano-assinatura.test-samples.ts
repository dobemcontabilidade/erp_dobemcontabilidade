import { ICalculoPlanoAssinatura, NewCalculoPlanoAssinatura } from './calculo-plano-assinatura.model';

export const sampleWithRequiredData: ICalculoPlanoAssinatura = {
  id: 23237,
};

export const sampleWithPartialData: ICalculoPlanoAssinatura = {
  id: 16444,
  valorEnquadramento: 16749.18,
  valorFuncionarios: 1065.76,
  valorFaturamento: 8823.29,
  valorPlanoContabil: 8506.55,
  valorPeriodo: 30416.02,
  valorAno: 13061.82,
};

export const sampleWithFullData: ICalculoPlanoAssinatura = {
  id: 24345,
  codigoAtendimento: 'acrylic',
  valorEnquadramento: 23002.51,
  valorTributacao: 30709.34,
  valorRamo: 1319.25,
  valorFuncionarios: 15489.87,
  valorSocios: 14540.51,
  valorFaturamento: 17581.22,
  valorPlanoContabil: 22991.9,
  valorPlanoContabilComDesconto: 16707.12,
  valorMensalidade: 15045.89,
  valorPeriodo: 30531.3,
  valorAno: 22446.29,
};

export const sampleWithNewData: NewCalculoPlanoAssinatura = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
