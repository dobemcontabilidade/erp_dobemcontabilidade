import { IDenuncia, NewDenuncia } from './denuncia.model';

export const sampleWithRequiredData: IDenuncia = {
  id: 29684,
  titulo: 'helpfully burden buzzing',
  mensagem: 'whenever ack',
};

export const sampleWithPartialData: IDenuncia = {
  id: 32146,
  titulo: 'boohoo',
  mensagem: 'firebomb',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IDenuncia = {
  id: 10532,
  titulo: 'huzzah tenor decline',
  mensagem: 'commandment about',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDenuncia = {
  titulo: 'variable',
  mensagem: 'eek gladly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
