import { IBancoContador, NewBancoContador } from './banco-contador.model';

export const sampleWithRequiredData: IBancoContador = {
  id: 20068,
  agencia: 'miserably perk but',
  conta: 'presence inside',
};

export const sampleWithPartialData: IBancoContador = {
  id: 26150,
  agencia: 'as brief yum',
  conta: 'ah across',
};

export const sampleWithFullData: IBancoContador = {
  id: 28024,
  agencia: 'noxious hm daintily',
  conta: 'majestically reverse cosset',
  digitoAgencia: 'viciously',
  digitoConta: 'than reliable',
  principal: false,
};

export const sampleWithNewData: NewBancoContador = {
  agencia: 'delightfully',
  conta: 'nicely before sidetrack',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
