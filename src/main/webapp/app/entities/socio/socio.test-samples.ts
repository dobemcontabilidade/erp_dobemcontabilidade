import { ISocio, NewSocio } from './socio.model';

export const sampleWithRequiredData: ISocio = {
  id: 28852,
  adminstrador: false,
  funcaoSocio: 'SOCIO_GERENTE',
};

export const sampleWithPartialData: ISocio = {
  id: 6835,
  nome: 'whoa circa',
  percentualSociedade: 18177.47,
  adminstrador: false,
  responsavelReceita: true,
  percentualDistribuicaoLucro: 22160.36,
  funcaoSocio: 'SOCIO',
};

export const sampleWithFullData: ISocio = {
  id: 7352,
  nome: 'zowie',
  prolabore: true,
  percentualSociedade: 32367.55,
  adminstrador: false,
  distribuicaoLucro: true,
  responsavelReceita: true,
  percentualDistribuicaoLucro: 8652.25,
  funcaoSocio: 'SOCIO',
};

export const sampleWithNewData: NewSocio = {
  adminstrador: true,
  funcaoSocio: 'SOCIO_GERENTE',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
