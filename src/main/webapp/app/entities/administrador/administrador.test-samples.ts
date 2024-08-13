import { IAdministrador, NewAdministrador } from './administrador.model';

export const sampleWithRequiredData: IAdministrador = {
  id: 13591,
  sobrenome: 'void',
};

export const sampleWithPartialData: IAdministrador = {
  id: 1249,
  nome: 'flickering feminize',
  sobrenome: 'like bad candid',
};

export const sampleWithFullData: IAdministrador = {
  id: 29762,
  nome: 'locality brightly',
  sobrenome: 'lumpy',
  funcao: 'spicy cramp',
};

export const sampleWithNewData: NewAdministrador = {
  sobrenome: 'inside questionably',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
