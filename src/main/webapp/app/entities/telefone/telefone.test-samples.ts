import { ITelefone, NewTelefone } from './telefone.model';

export const sampleWithRequiredData: ITelefone = {
  id: 5612,
  codigoArea: 'associate',
  telefone: 'jolly gleefully',
};

export const sampleWithPartialData: ITelefone = {
  id: 13310,
  codigoArea: 'if boldly rejuvenate',
  telefone: 'of scattering',
  principla: true,
};

export const sampleWithFullData: ITelefone = {
  id: 597,
  codigoArea: 'generalise psst dolphin',
  telefone: 'or sneakers embrace',
  principla: true,
  tipoTelefone: 'CELULAR',
};

export const sampleWithNewData: NewTelefone = {
  codigoArea: 'familiarize speedily hmph',
  telefone: 'huzzah',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
