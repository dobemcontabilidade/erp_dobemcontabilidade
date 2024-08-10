import dayjs from 'dayjs/esm';

import { IAssinaturaEmpresa, NewAssinaturaEmpresa } from './assinatura-empresa.model';

export const sampleWithRequiredData: IAssinaturaEmpresa = {
  id: 7737,
};

export const sampleWithPartialData: IAssinaturaEmpresa = {
  id: 28976,
  valorEnquadramento: 22759.38,
  valorRamo: 12170.9,
  valorFuncionarios: 12611.15,
  valorPeriodo: 32683.15,
  valorAno: 13557.2,
  dataContratacao: dayjs('2024-08-09T18:42'),
  dataEncerramento: dayjs('2024-08-10T00:34'),
  diaVencimento: 19529,
  tipoContrato: 'CONTRATACAO_PLANO',
};

export const sampleWithFullData: IAssinaturaEmpresa = {
  id: 28473,
  codigoAssinatura: 'dependent bitterly',
  valorEnquadramento: 4393.65,
  valorTributacao: 122.91,
  valorRamo: 425.61,
  valorFuncionarios: 13000.3,
  valorSocios: 20824.64,
  valorFaturamento: 7889.17,
  valorPlanoContabil: 23586.2,
  valorPlanoContabilComDesconto: 21742.98,
  valorMensalidade: 20698.06,
  valorPeriodo: 20398.4,
  valorAno: 5700.03,
  dataContratacao: dayjs('2024-08-10T03:08'),
  dataEncerramento: dayjs('2024-08-09T11:42'),
  diaVencimento: 2924,
  situacao: 'CANCELADO',
  tipoContrato: 'CONTRATACAO_PLANO',
};

export const sampleWithNewData: NewAssinaturaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
