import { IDependentesFuncionario, NewDependentesFuncionario } from './dependentes-funcionario.model';

export const sampleWithRequiredData: IDependentesFuncionario = {
  id: 11612,
};

export const sampleWithPartialData: IDependentesFuncionario = {
  id: 12593,
  dependenteIRRF: true,
  dependenteSalarioFamilia: false,
};

export const sampleWithFullData: IDependentesFuncionario = {
  id: 592,
  urlCertidaoDependente: 'well-lit which yearningly',
  urlRgDependente: 'scarcely ouch by',
  dependenteIRRF: true,
  dependenteSalarioFamilia: false,
  tipoDependenteFuncionarioEnum: 'FILHO',
};

export const sampleWithNewData: NewDependentesFuncionario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
