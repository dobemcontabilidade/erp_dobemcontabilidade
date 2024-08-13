import { IParcelamentoImposto, NewParcelamentoImposto } from './parcelamento-imposto.model';

export const sampleWithRequiredData: IParcelamentoImposto = {
  id: 20254,
};

export const sampleWithPartialData: IParcelamentoImposto = {
  id: 15955,
  numeroParcelas: 7297,
  urlArquivoNegociacao: 'insidious potable',
  numeroParcelasPagas: 13995,
  numeroParcelasRegatantes: 1227,
  situacaoSolicitacaoParcelamentoEnum: 'CANCELADA',
};

export const sampleWithFullData: IParcelamentoImposto = {
  id: 3890,
  diaVencimento: 12568,
  numeroParcelas: 25552,
  urlArquivoNegociacao: 'abnormally worth accidentally',
  numeroParcelasPagas: 10242,
  numeroParcelasRegatantes: 30678,
  situacaoSolicitacaoParcelamentoEnum: 'PROCESSADA',
};

export const sampleWithNewData: NewParcelamentoImposto = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
