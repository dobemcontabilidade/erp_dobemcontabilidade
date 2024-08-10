import dayjs from 'dayjs/esm';

import { IUsuarioErp, NewUsuarioErp } from './usuario-erp.model';

export const sampleWithRequiredData: IUsuarioErp = {
  id: 1561,
  email: 'Hugo63@live.com',
  senha: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IUsuarioErp = {
  id: 8459,
  email: 'Roberto_Reis61@live.com',
  senha: '../fake-data/blob/hipster.txt',
  situacao: 'BLOQUEADO',
};

export const sampleWithFullData: IUsuarioErp = {
  id: 18953,
  email: 'Lorraine.Barros@yahoo.com',
  senha: '../fake-data/blob/hipster.txt',
  dataHoraAtivacao: dayjs('2024-08-09T23:35'),
  dataLimiteAcesso: dayjs('2024-08-09T06:10'),
  situacao: 'ATIVO',
};

export const sampleWithNewData: NewUsuarioErp = {
  email: 'Isis99@gmail.com',
  senha: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
