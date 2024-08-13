import { IImpostoParcelado, NewImpostoParcelado } from './imposto-parcelado.model';

export const sampleWithRequiredData: IImpostoParcelado = {
  id: 26299,
};

export const sampleWithPartialData: IImpostoParcelado = {
  id: 23414,
};

export const sampleWithFullData: IImpostoParcelado = {
  id: 15790,
  diasAtraso: 23758,
};

export const sampleWithNewData: NewImpostoParcelado = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
