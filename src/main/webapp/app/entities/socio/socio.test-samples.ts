import { ISocio, NewSocio } from './socio.model';

export const sampleWithRequiredData: ISocio = {
  id: 26511,
  adminstrador: true,
  funcaoSocio: 'SOCIOGERENTE',
};

export const sampleWithPartialData: ISocio = {
  id: 6187,
  prolabore: false,
  percentualSociedade: 20630.77,
  adminstrador: true,
  distribuicaoLucro: false,
  responsavelReceita: true,
  funcaoSocio: 'SOCIOGERENTE',
};

export const sampleWithFullData: ISocio = {
  id: 17664,
  prolabore: true,
  percentualSociedade: 9278.61,
  adminstrador: true,
  distribuicaoLucro: true,
  responsavelReceita: false,
  percentualDistribuicaoLucro: 25571.12,
  funcaoSocio: 'SOCIOGERENTE',
};

export const sampleWithNewData: NewSocio = {
  adminstrador: false,
  funcaoSocio: 'SOCIOADMINISTRADOR',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
