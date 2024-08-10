import { IAtividadeEmpresa, NewAtividadeEmpresa } from './atividade-empresa.model';

export const sampleWithRequiredData: IAtividadeEmpresa = {
  id: 23652,
};

export const sampleWithPartialData: IAtividadeEmpresa = {
  id: 27992,
  principal: true,
  ordem: 21133,
  descricaoAtividade: 'beautifully tenant',
};

export const sampleWithFullData: IAtividadeEmpresa = {
  id: 29979,
  principal: true,
  ordem: 27049,
  descricaoAtividade: 'dangerous',
};

export const sampleWithNewData: NewAtividadeEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
