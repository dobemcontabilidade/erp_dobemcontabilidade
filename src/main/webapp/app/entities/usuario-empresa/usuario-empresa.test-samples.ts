import dayjs from 'dayjs/esm';

import { IUsuarioEmpresa, NewUsuarioEmpresa } from './usuario-empresa.model';

export const sampleWithRequiredData: IUsuarioEmpresa = {
  id: 3650,
  email: 'Deneval.Silva47@bol.com.br',
  senha: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IUsuarioEmpresa = {
  id: 11443,
  email: 'Emanuel.Reis3@bol.com.br',
  senha: '../fake-data/blob/hipster.txt',
  dataHoraAtivacao: dayjs('2024-08-10T03:25'),
  situacao: 'EXCLUIDO',
};

export const sampleWithFullData: IUsuarioEmpresa = {
  id: 32441,
  email: 'Emanuelly99@gmail.com',
  senha: '../fake-data/blob/hipster.txt',
  token: '../fake-data/blob/hipster.txt',
  dataHoraAtivacao: dayjs('2024-08-09T17:14'),
  dataLimiteAcesso: dayjs('2024-08-09T12:16'),
  situacao: 'ATIVO',
};

export const sampleWithNewData: NewUsuarioEmpresa = {
  email: 'MariaEduarda_Macedo77@yahoo.com',
  senha: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
