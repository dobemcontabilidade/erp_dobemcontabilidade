import { IAdministrador, NewAdministrador } from './administrador.model';

export const sampleWithRequiredData: IAdministrador = {
  id: 9822,
  sobreNome: 'obtrude wheel',
};

export const sampleWithPartialData: IAdministrador = {
  id: 1185,
  nome: 'daintily victoriously',
  sobreNome: 'dynamite',
  funcao: 'yowza blissfully',
};

export const sampleWithFullData: IAdministrador = {
  id: 28465,
  nome: 'the gorilla',
  sobreNome: 'but narrate anchovy',
  funcao: 'once rust extremely',
};

export const sampleWithNewData: NewAdministrador = {
  sobreNome: 'temp than what',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
