import { IFeedBackUsuarioParaContador, NewFeedBackUsuarioParaContador } from './feed-back-usuario-para-contador.model';

export const sampleWithRequiredData: IFeedBackUsuarioParaContador = {
  id: 15690,
};

export const sampleWithPartialData: IFeedBackUsuarioParaContador = {
  id: 11168,
  comentario: 'boo concerning wrongly',
};

export const sampleWithFullData: IFeedBackUsuarioParaContador = {
  id: 24353,
  comentario: 'brick',
  pontuacao: 16817.74,
};

export const sampleWithNewData: NewFeedBackUsuarioParaContador = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
