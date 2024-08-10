import { IDepartamentoContador, NewDepartamentoContador } from './departamento-contador.model';

export const sampleWithRequiredData: IDepartamentoContador = {
  id: 13245,
};

export const sampleWithPartialData: IDepartamentoContador = {
  id: 4204,
  descricaoExperiencia: 'neglected before',
};

export const sampleWithFullData: IDepartamentoContador = {
  id: 24067,
  percentualExperiencia: 24063.45,
  descricaoExperiencia: 'extremely',
  pontuacaoEntrevista: 30735.14,
  pontuacaoAvaliacao: 8081.92,
};

export const sampleWithNewData: NewDepartamentoContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
