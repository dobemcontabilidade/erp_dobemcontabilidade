import { IDepartamentoFuncionario, NewDepartamentoFuncionario } from './departamento-funcionario.model';

export const sampleWithRequiredData: IDepartamentoFuncionario = {
  id: 14910,
  cargo: 'heel onto occasion',
};

export const sampleWithPartialData: IDepartamentoFuncionario = {
  id: 21764,
  cargo: 'faint simplistic eligibility',
};

export const sampleWithFullData: IDepartamentoFuncionario = {
  id: 12387,
  cargo: 'shove ritual absent',
};

export const sampleWithNewData: NewDepartamentoFuncionario = {
  cargo: 'pump',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
