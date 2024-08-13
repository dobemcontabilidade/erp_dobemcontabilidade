import { IAgendaContadorConfig, NewAgendaContadorConfig } from './agenda-contador-config.model';

export const sampleWithRequiredData: IAgendaContadorConfig = {
  id: 6790,
};

export const sampleWithPartialData: IAgendaContadorConfig = {
  id: 25945,
};

export const sampleWithFullData: IAgendaContadorConfig = {
  id: 7896,
  ativo: false,
  tipoVisualizacaoAgendaEnum: 'SEMANAL',
};

export const sampleWithNewData: NewAgendaContadorConfig = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
