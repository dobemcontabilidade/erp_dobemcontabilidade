import { ISegmentoCnae, NewSegmentoCnae } from './segmento-cnae.model';

export const sampleWithRequiredData: ISegmentoCnae = {
  id: 30338,
  descricao: '../fake-data/blob/hipster.txt',
  tipo: 'SERVICO',
};

export const sampleWithPartialData: ISegmentoCnae = {
  id: 7085,
  nome: 'promptly ha boo',
  descricao: '../fake-data/blob/hipster.txt',
  imagem: 'idea though whose',
  tipo: 'SERVICO_COMERCIO',
};

export const sampleWithFullData: ISegmentoCnae = {
  id: 11298,
  nome: 'while glimmer diaper',
  descricao: '../fake-data/blob/hipster.txt',
  icon: 'ugh',
  imagem: 'offset sophisticated bayou',
  tags: '../fake-data/blob/hipster.txt',
  tipo: 'COMERCIO',
};

export const sampleWithNewData: NewSegmentoCnae = {
  descricao: '../fake-data/blob/hipster.txt',
  tipo: 'SERVICO_COMERCIO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
