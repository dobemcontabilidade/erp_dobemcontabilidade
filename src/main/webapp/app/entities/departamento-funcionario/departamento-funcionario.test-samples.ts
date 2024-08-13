import { IDepartamentoFuncionario, NewDepartamentoFuncionario } from './departamento-funcionario.model';

export const sampleWithRequiredData: IDepartamentoFuncionario = {
  id: 19346,
  cargo: 'lone arrangement if',
};

export const sampleWithPartialData: IDepartamentoFuncionario = {
  id: 26056,
  cargo: 'versus ugh reparation',
};

export const sampleWithFullData: IDepartamentoFuncionario = {
  id: 23447,
  cargo: 'whereas catsup laze',
};

export const sampleWithNewData: NewDepartamentoFuncionario = {
  cargo: 'unwitting hmph longingly',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
