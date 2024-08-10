import { ISegmentoCnae, NewSegmentoCnae } from './segmento-cnae.model';

export const sampleWithRequiredData: ISegmentoCnae = {
  id: 9268,
  descricao: '../fake-data/blob/hipster.txt',
  tipo: 'SERVICO_COMERCIO',
};

export const sampleWithPartialData: ISegmentoCnae = {
  id: 29366,
  nome: 'hence seagull',
  descricao: '../fake-data/blob/hipster.txt',
  imagem: 'ick',
  tipo: 'COMERCIO',
};

export const sampleWithFullData: ISegmentoCnae = {
  id: 26384,
  nome: 'apud smoodge',
  descricao: '../fake-data/blob/hipster.txt',
  icon: 'within huzzah upon',
  imagem: 'aw pfft',
  tags: '../fake-data/blob/hipster.txt',
  tipo: 'COMERCIO',
};

export const sampleWithNewData: NewSegmentoCnae = {
  descricao: '../fake-data/blob/hipster.txt',
  tipo: 'COMERCIO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
