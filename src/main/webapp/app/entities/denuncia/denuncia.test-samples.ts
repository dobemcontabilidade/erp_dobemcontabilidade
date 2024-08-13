import { IDenuncia, NewDenuncia } from './denuncia.model';

export const sampleWithRequiredData: IDenuncia = {
  id: 22619,
  titulo: 'whenever granular',
  mensagem: 'mukluk drat afterwards',
};

export const sampleWithPartialData: IDenuncia = {
  id: 31814,
  titulo: 'ah',
  mensagem: 'astride why',
};

export const sampleWithFullData: IDenuncia = {
  id: 4720,
  titulo: 'usable huzzah ew',
  mensagem: 'sampan',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewDenuncia = {
  titulo: 'ack sensitise',
  mensagem: 'meanwhile',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
