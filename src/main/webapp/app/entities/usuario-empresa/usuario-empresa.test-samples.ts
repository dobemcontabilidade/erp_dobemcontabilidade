import dayjs from 'dayjs/esm';

import { IUsuarioEmpresa, NewUsuarioEmpresa } from './usuario-empresa.model';

export const sampleWithRequiredData: IUsuarioEmpresa = {
  id: 21311,
  email: 'Pietro37@hotmail.com',
  senha: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IUsuarioEmpresa = {
  id: 12839,
  email: 'Sarah.Moreira@hotmail.com',
  senha: '../fake-data/blob/hipster.txt',
  dataHoraAtivacao: dayjs('2024-08-12T05:31'),
};

export const sampleWithFullData: IUsuarioEmpresa = {
  id: 29968,
  email: 'Liz.Pereira@gmail.com',
  senha: '../fake-data/blob/hipster.txt',
  token: '../fake-data/blob/hipster.txt',
  ativo: false,
  dataHoraAtivacao: dayjs('2024-08-12T16:00'),
  dataLimiteAcesso: dayjs('2024-08-13T03:07'),
  situacaoUsuarioEmpresa: 'ATIVO',
  tipoUsuarioEmpresaEnum: 'SOCIO',
};

export const sampleWithNewData: NewUsuarioEmpresa = {
  email: 'Hugo.Martins2@bol.com.br',
  senha: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
