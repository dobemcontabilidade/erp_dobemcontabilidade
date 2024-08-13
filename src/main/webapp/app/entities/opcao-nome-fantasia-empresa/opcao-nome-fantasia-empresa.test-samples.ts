import { IOpcaoNomeFantasiaEmpresa, NewOpcaoNomeFantasiaEmpresa } from './opcao-nome-fantasia-empresa.model';

export const sampleWithRequiredData: IOpcaoNomeFantasiaEmpresa = {
  id: 5091,
  nome: 'huzzah article',
};

export const sampleWithPartialData: IOpcaoNomeFantasiaEmpresa = {
  id: 3255,
  nome: 'retest',
  selecionado: true,
};

export const sampleWithFullData: IOpcaoNomeFantasiaEmpresa = {
  id: 7046,
  nome: 'lest',
  ordem: 20078,
  selecionado: false,
};

export const sampleWithNewData: NewOpcaoNomeFantasiaEmpresa = {
  nome: 'woot spray zowie',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
