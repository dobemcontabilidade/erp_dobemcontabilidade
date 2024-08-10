import dayjs from 'dayjs/esm';

import { IUsuarioGestao, NewUsuarioGestao } from './usuario-gestao.model';

export const sampleWithRequiredData: IUsuarioGestao = {
  id: 16564,
  email: 'JulioCesar.Martins73@yahoo.com',
  senha: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IUsuarioGestao = {
  id: 21330,
  email: 'MariaLuiza_Albuquerque@yahoo.com',
  senha: '../fake-data/blob/hipster.txt',
  situacao: 'EXCLUIDO',
};

export const sampleWithFullData: IUsuarioGestao = {
  id: 13252,
  email: 'Eduardo32@yahoo.com',
  senha: '../fake-data/blob/hipster.txt',
  token: '../fake-data/blob/hipster.txt',
  dataHoraAtivacao: dayjs('2024-08-10T03:03'),
  dataLimiteAcesso: dayjs('2024-08-09T14:39'),
  situacao: 'ATIVO',
};

export const sampleWithNewData: NewUsuarioGestao = {
  email: 'Laura.Martins62@live.com',
  senha: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
