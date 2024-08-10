import { IObservacaoCnae, NewObservacaoCnae } from './observacao-cnae.model';

export const sampleWithRequiredData: IObservacaoCnae = {
  id: 6198,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithPartialData: IObservacaoCnae = {
  id: 4126,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithFullData: IObservacaoCnae = {
  id: 23513,
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewObservacaoCnae = {
  descricao: '../fake-data/blob/hipster.txt',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
