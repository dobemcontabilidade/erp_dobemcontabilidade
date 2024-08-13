import { IAtividadeEmpresa, NewAtividadeEmpresa } from './atividade-empresa.model';

export const sampleWithRequiredData: IAtividadeEmpresa = {
  id: 19135,
};

export const sampleWithPartialData: IAtividadeEmpresa = {
  id: 13704,
  principal: false,
  ordem: 13507,
};

export const sampleWithFullData: IAtividadeEmpresa = {
  id: 22617,
  principal: false,
  ordem: 1539,
  descricaoAtividade: 'via',
};

export const sampleWithNewData: NewAtividadeEmpresa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
