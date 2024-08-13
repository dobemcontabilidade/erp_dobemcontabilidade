import { IDepartamentoContador, NewDepartamentoContador } from './departamento-contador.model';

export const sampleWithRequiredData: IDepartamentoContador = {
  id: 28326,
};

export const sampleWithPartialData: IDepartamentoContador = {
  id: 27275,
  percentualExperiencia: 11059.89,
};

export const sampleWithFullData: IDepartamentoContador = {
  id: 20473,
  percentualExperiencia: 4279.49,
  descricaoExperiencia: 'stealthily gee',
  pontuacaoEntrevista: 20185.07,
  pontuacaoAvaliacao: 26800.05,
};

export const sampleWithNewData: NewDepartamentoContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
