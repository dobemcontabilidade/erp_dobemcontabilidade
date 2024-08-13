import { IOpcaoRazaoSocialEmpresa, NewOpcaoRazaoSocialEmpresa } from './opcao-razao-social-empresa.model';

export const sampleWithRequiredData: IOpcaoRazaoSocialEmpresa = {
  id: 28689,
  nome: 'before pfft',
};

export const sampleWithPartialData: IOpcaoRazaoSocialEmpresa = {
  id: 7931,
  nome: 'wherever never',
  ordem: 26760,
  selecionado: false,
};

export const sampleWithFullData: IOpcaoRazaoSocialEmpresa = {
  id: 4203,
  nome: 'sometimes meaty mmm',
  ordem: 6338,
  selecionado: false,
};

export const sampleWithNewData: NewOpcaoRazaoSocialEmpresa = {
  nome: 'anenst provision armadillo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
