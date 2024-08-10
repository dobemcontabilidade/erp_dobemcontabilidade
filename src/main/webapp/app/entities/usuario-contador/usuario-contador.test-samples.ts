import dayjs from 'dayjs/esm';

import { IUsuarioContador, NewUsuarioContador } from './usuario-contador.model';

export const sampleWithRequiredData: IUsuarioContador = {
  id: 8369,
  email: 'Breno16@gmail.com',
  senha: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IUsuarioContador = {
  id: 5996,
  email: 'Isadora47@gmail.com',
  senha: '../fake-data/blob/hipster.txt',
  dataHoraAtivacao: dayjs('2024-08-10T02:26'),
};

export const sampleWithFullData: IUsuarioContador = {
  id: 20159,
  email: 'Karla.Moreira@bol.com.br',
  senha: '../fake-data/blob/hipster.txt',
  token: '../fake-data/blob/hipster.txt',
  dataHoraAtivacao: dayjs('2024-08-09T20:05'),
  dataLimiteAcesso: dayjs('2024-08-09T15:43'),
  situacao: 'BLOQUEADO',
};

export const sampleWithNewData: NewUsuarioContador = {
  email: 'Vitor.Albuquerque@gmail.com',
  senha: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
