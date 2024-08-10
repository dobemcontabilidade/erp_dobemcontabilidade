import dayjs from 'dayjs/esm';

import { IAssinaturaEmpresa, NewAssinaturaEmpresa } from './assinatura-empresa.model';

export const sampleWithRequiredData: IAssinaturaEmpresa = {
  id: 20235,
};

export const sampleWithPartialData: IAssinaturaEmpresa = {
  id: 12171,
  codigoAssinatura: 'hmph nautical',
  valorTributacao: 6217.82,
  valorRamo: 14830.51,
  valorPlanoContaAzulComDesconto: 29900.25,
  valorMensalidade: 18795.7,
  valorPeriodo: 1018.92,
  valorAno: 29064.41,
  dataContratacao: dayjs('2024-08-10T01:28'),
  diaVencimento: 1748,
};

export const sampleWithFullData: IAssinaturaEmpresa = {
  id: 4393,
  codigoAssinatura: 'parallelogram',
  valorEnquadramento: 20398.4,
  valorTributacao: 5700.03,
  valorRamo: 2388.76,
  valorFuncionarios: 23458.35,
  valorSocios: 2924.46,
  valorFaturamento: 26113.9,
  valorPlanoContabil: 30297.7,
  valorPlanoContabilComDesconto: 6114.55,
  valorPlanoContaAzulComDesconto: 26677.01,
  valorMensalidade: 27654.73,
  valorPeriodo: 16507.4,
  valorAno: 27466.18,
  dataContratacao: dayjs('2024-08-09T07:30'),
  dataEncerramento: dayjs('2024-08-09T18:58'),
  diaVencimento: 16042,
  situacao: 'EXCLUIDO',
  tipoContrato: 'CONTRATACAO_PLANO',
};

export const sampleWithNewData: NewAssinaturaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
