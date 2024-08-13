import { IEmpresaModelo, NewEmpresaModelo } from './empresa-modelo.model';

export const sampleWithRequiredData: IEmpresaModelo = {
  id: 5464,
};

export const sampleWithPartialData: IEmpresaModelo = {
  id: 21514,
  observacao: 'chassis yearningly nut',
};

export const sampleWithFullData: IEmpresaModelo = {
  id: 16555,
  nome: 'whenever reminisce verbally',
  observacao: 'what',
};

export const sampleWithNewData: NewEmpresaModelo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
