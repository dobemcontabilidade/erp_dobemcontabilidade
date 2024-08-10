import { IOpcaoNomeFantasiaEmpresa, NewOpcaoNomeFantasiaEmpresa } from './opcao-nome-fantasia-empresa.model';

export const sampleWithRequiredData: IOpcaoNomeFantasiaEmpresa = {
  id: 4953,
  nome: 'hasty besides',
};

export const sampleWithPartialData: IOpcaoNomeFantasiaEmpresa = {
  id: 24452,
  nome: 'urgently spinach',
};

export const sampleWithFullData: IOpcaoNomeFantasiaEmpresa = {
  id: 18831,
  nome: 'worth larch',
  ordem: 32494,
  selecionado: true,
};

export const sampleWithNewData: NewOpcaoNomeFantasiaEmpresa = {
  nome: 'exacerbate record wrap',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
