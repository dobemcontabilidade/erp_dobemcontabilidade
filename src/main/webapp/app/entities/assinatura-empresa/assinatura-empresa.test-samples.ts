import dayjs from 'dayjs/esm';

import { IAssinaturaEmpresa, NewAssinaturaEmpresa } from './assinatura-empresa.model';

export const sampleWithRequiredData: IAssinaturaEmpresa = {
  id: 6990,
};

export const sampleWithPartialData: IAssinaturaEmpresa = {
  id: 2545,
  valorEnquadramento: 18409.13,
  valorTributacao: 11640.16,
  valorFuncionarios: 20006.69,
  valorPlanoContaAzulComDesconto: 29514.16,
  valorMensalidade: 16436.09,
  valorPeriodo: 15183.12,
  dataEncerramento: dayjs('2024-08-12T05:45'),
  diaVencimento: 1568,
  situacaoContratoEmpresa: 'INATIVO',
  tipoContrato: 'CONTRATACAOPLANO',
};

export const sampleWithFullData: IAssinaturaEmpresa = {
  id: 25621,
  codigoAssinatura: 'rattle imbue but',
  valorEnquadramento: 13893.6,
  valorTributacao: 20474.81,
  valorRamo: 12025.27,
  valorFuncionarios: 18712.47,
  valorSocios: 6860.11,
  valorFaturamento: 20461.97,
  valorPlanoContabil: 29456.9,
  valorPlanoContabilComDesconto: 18319.94,
  valorPlanoContaAzulComDesconto: 6714.96,
  valorMensalidade: 5580.1,
  valorPeriodo: 27748.51,
  valorAno: 3134.31,
  dataContratacao: dayjs('2024-08-12T10:04'),
  dataEncerramento: dayjs('2024-08-12T09:03'),
  diaVencimento: 22042,
  situacaoContratoEmpresa: 'INATIVO',
  tipoContrato: 'CONTRATACAOPLANO',
};

export const sampleWithNewData: NewAssinaturaEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
