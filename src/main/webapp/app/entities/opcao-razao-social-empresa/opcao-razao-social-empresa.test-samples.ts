import { IOpcaoRazaoSocialEmpresa, NewOpcaoRazaoSocialEmpresa } from './opcao-razao-social-empresa.model';

export const sampleWithRequiredData: IOpcaoRazaoSocialEmpresa = {
  id: 8664,
  nome: 'stint ferry',
};

export const sampleWithPartialData: IOpcaoRazaoSocialEmpresa = {
  id: 19972,
  nome: 'after',
  selecionado: true,
};

export const sampleWithFullData: IOpcaoRazaoSocialEmpresa = {
  id: 17451,
  nome: 'uh-huh brr waffle',
  ordem: 14694,
  selecionado: false,
};

export const sampleWithNewData: NewOpcaoRazaoSocialEmpresa = {
  nome: 'vegetarianism duh via',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
