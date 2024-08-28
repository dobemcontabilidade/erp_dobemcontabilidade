import { ITelefone, NewTelefone } from './telefone.model';

export const sampleWithRequiredData: ITelefone = {
  id: 31212,
  codigoArea: 'kangaroo as',
  telefone: 'for midst so',
};

export const sampleWithPartialData: ITelefone = {
  id: 26421,
  codigoArea: 'tonight',
  telefone: 'shocking theorize',
  tipoTelefone: 'CELULAR',
};

export const sampleWithFullData: ITelefone = {
  id: 4652,
  codigoArea: 'closed',
  telefone: 'hence as likewise',
  principla: true,
  tipoTelefone: 'COMERCIAL',
};

export const sampleWithNewData: NewTelefone = {
  codigoArea: 'wisely into below',
  telefone: 'webinar stunning well',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
