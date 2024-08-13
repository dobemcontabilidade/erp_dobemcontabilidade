import dayjs from 'dayjs/esm';

import { IUsuarioContador, NewUsuarioContador } from './usuario-contador.model';

export const sampleWithRequiredData: IUsuarioContador = {
  id: 16711,
  email: 'Davi.Macedo91@gmail.com',
  senha: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IUsuarioContador = {
  id: 27926,
  email: 'Suelen.Barros58@bol.com.br',
  senha: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IUsuarioContador = {
  id: 19218,
  email: 'Gael.Carvalho91@yahoo.com',
  senha: '../fake-data/blob/hipster.txt',
  token: '../fake-data/blob/hipster.txt',
  ativo: true,
  dataHoraAtivacao: dayjs('2024-08-13T04:51'),
  dataLimiteAcesso: dayjs('2024-08-13T01:55'),
  situacao: 'BLOQUEADO',
};

export const sampleWithNewData: NewUsuarioContador = {
  email: 'Janaina44@yahoo.com',
  senha: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
