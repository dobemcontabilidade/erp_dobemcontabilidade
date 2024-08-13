import { IAgenteIntegracaoEstagio, NewAgenteIntegracaoEstagio } from './agente-integracao-estagio.model';

export const sampleWithRequiredData: IAgenteIntegracaoEstagio = {
  id: 22389,
};

export const sampleWithPartialData: IAgenteIntegracaoEstagio = {
  id: 6325,
  coordenador: 'pricey',
  cpfCoordenadorEstagio: 'juvenile sift',
  bairro: 'excluding unto kale',
  cep: 'promptly ',
  principal: true,
};

export const sampleWithFullData: IAgenteIntegracaoEstagio = {
  id: 14590,
  cnpj: 'shoreline long-term',
  razaoSocial: 'quarrelsome',
  coordenador: 'above along ah',
  cpfCoordenadorEstagio: 'wiggly',
  logradouro: 'sulks proper phew',
  numero: 'daint',
  complemento: 'properly',
  bairro: 'hyphenate',
  cep: 'although',
  principal: false,
};

export const sampleWithNewData: NewAgenteIntegracaoEstagio = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
