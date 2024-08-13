import { IEtapaFluxoModelo, NewEtapaFluxoModelo } from './etapa-fluxo-modelo.model';

export const sampleWithRequiredData: IEtapaFluxoModelo = {
  id: 17828,
};

export const sampleWithPartialData: IEtapaFluxoModelo = {
  id: 18895,
  nome: 'aha',
  descricao: 'stable',
};

export const sampleWithFullData: IEtapaFluxoModelo = {
  id: 1399,
  nome: 'circa meanwhile',
  descricao: 'forenenst partially avaricious',
  ordem: 2454,
};

export const sampleWithNewData: NewEtapaFluxoModelo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
