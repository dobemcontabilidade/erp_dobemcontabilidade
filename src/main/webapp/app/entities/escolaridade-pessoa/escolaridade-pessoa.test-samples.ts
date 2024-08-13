import { IEscolaridadePessoa, NewEscolaridadePessoa } from './escolaridade-pessoa.model';

export const sampleWithRequiredData: IEscolaridadePessoa = {
  id: 14605,
  nomeInstituicao: 'terribly uh-huh but',
};

export const sampleWithPartialData: IEscolaridadePessoa = {
  id: 22959,
  nomeInstituicao: 'once brief',
  anoConclusao: 20691,
};

export const sampleWithFullData: IEscolaridadePessoa = {
  id: 21502,
  nomeInstituicao: 'spicy even behind',
  anoConclusao: 9737,
  urlComprovanteEscolaridade: 'in unfortunate between',
};

export const sampleWithNewData: NewEscolaridadePessoa = {
  nomeInstituicao: 'pish true',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
