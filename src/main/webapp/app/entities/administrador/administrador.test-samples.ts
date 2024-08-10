import { IAdministrador, NewAdministrador } from './administrador.model';

export const sampleWithRequiredData: IAdministrador = {
  id: 16711,
  sobreNome: 'gently eek',
};

export const sampleWithPartialData: IAdministrador = {
  id: 3817,
  sobreNome: 'consult',
  funcao: 'incomparable hyphenate what',
};

export const sampleWithFullData: IAdministrador = {
  id: 11140,
  nome: 'wherever',
  sobreNome: 'promptly against vouch',
  funcao: 'configuration',
};

export const sampleWithNewData: NewAdministrador = {
  sobreNome: 'contest',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
