import { ISocio, NewSocio } from './socio.model';

export const sampleWithRequiredData: ISocio = {
  id: 18575,
  adminstrador: false,
  funcaoSocio: 'SOCIO',
};

export const sampleWithPartialData: ISocio = {
  id: 10995,
  prolabore: true,
  percentualSociedade: 24259.29,
  adminstrador: true,
  distribuicaoLucro: false,
  responsavelReceita: true,
  percentualDistribuicaoLucro: 26114,
  funcaoSocio: 'SOCIO_GERENTE',
};

export const sampleWithFullData: ISocio = {
  id: 21590,
  nome: 'aha untimely',
  prolabore: false,
  percentualSociedade: 30477.96,
  adminstrador: false,
  distribuicaoLucro: true,
  responsavelReceita: true,
  percentualDistribuicaoLucro: 21067.73,
  funcaoSocio: 'SOCIO_ADMINISTRADOR',
};

export const sampleWithNewData: NewSocio = {
  adminstrador: false,
  funcaoSocio: 'SOCIO_GERENTE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
