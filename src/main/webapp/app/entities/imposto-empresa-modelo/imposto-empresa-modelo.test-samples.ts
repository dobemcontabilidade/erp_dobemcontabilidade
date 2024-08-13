import { IImpostoEmpresaModelo, NewImpostoEmpresaModelo } from './imposto-empresa-modelo.model';

export const sampleWithRequiredData: IImpostoEmpresaModelo = {
  id: 15385,
};

export const sampleWithPartialData: IImpostoEmpresaModelo = {
  id: 32172,
  nome: 'whose snip',
  observacao: 'why bulk',
};

export const sampleWithFullData: IImpostoEmpresaModelo = {
  id: 15217,
  nome: 'which swot between',
  observacao: 'fantastic than whether',
};

export const sampleWithNewData: NewImpostoEmpresaModelo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
