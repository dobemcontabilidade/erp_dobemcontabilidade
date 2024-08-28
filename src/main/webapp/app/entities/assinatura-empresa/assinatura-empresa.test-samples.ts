import dayjs from 'dayjs/esm';

import { IAssinaturaEmpresa, NewAssinaturaEmpresa } from './assinatura-empresa.model';

export const sampleWithRequiredData: IAssinaturaEmpresa = {
  id: 27580,
  razaoSocial: 'phooey flair equally',
};

export const sampleWithPartialData: IAssinaturaEmpresa = {
  id: 4755,
  razaoSocial: 'pfft inside',
  valorEnquadramento: 25204.71,
  valorSocios: 12319.32,
  valorPlanoContabil: 10845.03,
  valorPeriodo: 11304.57,
  valorAno: 17636.27,
  diaVencimento: 4608,
  situacao: 'EXCLUIDO',
};

export const sampleWithFullData: IAssinaturaEmpresa = {
  id: 22385,
  razaoSocial: 'coaster woot kosher',
  codigoAssinatura: 'plaster learn warning',
  valorEnquadramento: 9054.52,
  valorTributacao: 26393.78,
  valorRamo: 22219.01,
  valorFuncionarios: 22653.88,
  valorSocios: 7770.53,
  valorFaturamento: 25266.59,
  valorPlanoContabil: 6964.55,
  valorPlanoContabilComDesconto: 10939.89,
  valorPlanoContaAzulComDesconto: 22097.46,
  valorMensalidade: 23931.39,
  valorPeriodo: 8560.31,
  valorAno: 24504.35,
  dataContratacao: dayjs('2024-08-28T16:31'),
  dataEncerramento: dayjs('2024-08-28T03:22'),
  diaVencimento: 16776,
  situacao: 'EXCLUIDO',
  tipoContrato: 'CONTRATACAO_PLANO',
};

export const sampleWithNewData: NewAssinaturaEmpresa = {
  razaoSocial: 'adviser weigh',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
