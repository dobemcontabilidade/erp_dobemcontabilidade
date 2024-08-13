import { IFuncionario, NewFuncionario } from './funcionario.model';

export const sampleWithRequiredData: IFuncionario = {
  id: 2293,
};

export const sampleWithPartialData: IFuncionario = {
  id: 145,
  numeroPisNisPasep: 7679,
  multiploVinculos: false,
};

export const sampleWithFullData: IFuncionario = {
  id: 16856,
  numeroPisNisPasep: 7954,
  reintegrado: false,
  primeiroEmprego: true,
  multiploVinculos: false,
  dataOpcaoFgts: 'knottily ultimately phooey',
  filiacaoSindical: true,
  cnpjSindicato: 'mmm',
  tipoFuncionarioEnum: 'ESTAGIARIO',
};

export const sampleWithNewData: NewFuncionario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
