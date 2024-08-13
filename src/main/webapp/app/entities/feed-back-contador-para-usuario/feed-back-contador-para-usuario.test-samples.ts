import { IFeedBackContadorParaUsuario, NewFeedBackContadorParaUsuario } from './feed-back-contador-para-usuario.model';

export const sampleWithRequiredData: IFeedBackContadorParaUsuario = {
  id: 11554,
};

export const sampleWithPartialData: IFeedBackContadorParaUsuario = {
  id: 11199,
};

export const sampleWithFullData: IFeedBackContadorParaUsuario = {
  id: 18478,
  comentario: 'positively output',
  pontuacao: 2338,
};

export const sampleWithNewData: NewFeedBackContadorParaUsuario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
