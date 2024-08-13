import dayjs from 'dayjs/esm';

import { IUsuarioGestao, NewUsuarioGestao } from './usuario-gestao.model';

export const sampleWithRequiredData: IUsuarioGestao = {
  id: 1185,
  email: 'Talita_Braga@bol.com.br',
  senha: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IUsuarioGestao = {
  id: 14519,
  email: 'Caio.Batista@live.com',
  senha: '../fake-data/blob/hipster.txt',
  dataLimiteAcesso: dayjs('2024-08-13T01:30'),
  situacaoUsuarioGestao: 'BLOQUEADO',
};

export const sampleWithFullData: IUsuarioGestao = {
  id: 27244,
  email: 'Maria20@hotmail.com',
  senha: '../fake-data/blob/hipster.txt',
  token: '../fake-data/blob/hipster.txt',
  ativo: false,
  dataHoraAtivacao: dayjs('2024-08-12T20:26'),
  dataLimiteAcesso: dayjs('2024-08-12T17:52'),
  situacaoUsuarioGestao: 'INATIVO',
};

export const sampleWithNewData: NewUsuarioGestao = {
  email: 'Bernardo_Costa12@yahoo.com',
  senha: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
