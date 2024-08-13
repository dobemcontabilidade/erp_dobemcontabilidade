import dayjs from 'dayjs/esm';

import { IPerfilAcessoUsuario, NewPerfilAcessoUsuario } from './perfil-acesso-usuario.model';

export const sampleWithRequiredData: IPerfilAcessoUsuario = {
  id: 7813,
};

export const sampleWithPartialData: IPerfilAcessoUsuario = {
  id: 15154,
  dataExpiracao: dayjs('2024-08-12T13:44'),
};

export const sampleWithFullData: IPerfilAcessoUsuario = {
  id: 12687,
  nome: 'sturdy gadzooks real',
  autorizado: true,
  dataExpiracao: dayjs('2024-08-12T15:30'),
};

export const sampleWithNewData: NewPerfilAcessoUsuario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
