import { IFuncionario, NewFuncionario } from './funcionario.model';

export const sampleWithRequiredData: IFuncionario = {
  id: 16645,
};

export const sampleWithPartialData: IFuncionario = {
  id: 32135,
  nome: 'opposite',
  cargo: 'new',
};

export const sampleWithFullData: IFuncionario = {
  id: 14696,
  nome: 'pleased',
  salario: 31799.11,
  ctps: 'concerning gentle',
  cargo: 'next whenever',
  descricaoAtividades: '../fake-data/blob/hipster.txt',
  situacao: 'ADMITIDO',
};

export const sampleWithNewData: NewFuncionario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
