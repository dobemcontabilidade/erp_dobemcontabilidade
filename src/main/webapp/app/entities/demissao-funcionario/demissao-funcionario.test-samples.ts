import { IDemissaoFuncionario, NewDemissaoFuncionario } from './demissao-funcionario.model';

export const sampleWithRequiredData: IDemissaoFuncionario = {
  id: 9679,
};

export const sampleWithPartialData: IDemissaoFuncionario = {
  id: 10046,
  valorPensao: 'wall despite',
  valorPensaoFgts: 'towards',
  percentualFgts: 'unlike',
  diasAvisoPrevio: 30025,
  dataAvisoPrevio: 'hyperventilate',
  pagar13Recisao: true,
  jornadaTrabalhoCumpridaSemana: false,
  novoVinculoComprovado: false,
  dispensaAvisoPrevio: true,
  avisoPrevioTrabalhadoRecebido: false,
  avisoPrevioIndenizado: false,
};

export const sampleWithFullData: IDemissaoFuncionario = {
  id: 21561,
  numeroCertidaoObito: 'apprehensive',
  cnpjEmpresaSucessora: 'expectancy',
  saldoFGTS: 'deprecate greedily',
  valorPensao: 'er twinkle pish',
  valorPensaoFgts: 'ack silver morbid',
  percentualPensao: 'than of',
  percentualFgts: 'because bah oof',
  diasAvisoPrevio: 9136,
  dataAvisoPrevio: 'extreme',
  dataPagamento: 'cultured ironclad',
  dataAfastamento: 'reconcile knowingly ha',
  urlDemissional: 'wind-chime lest',
  calcularRecisao: false,
  pagar13Recisao: true,
  jornadaTrabalhoCumpridaSemana: false,
  sabadoCompesado: true,
  novoVinculoComprovado: true,
  dispensaAvisoPrevio: true,
  fgtsArrecadadoGuia: true,
  avisoPrevioTrabalhadoRecebido: true,
  recolherFgtsMesAnterior: false,
  avisoPrevioIndenizado: false,
  cumprimentoAvisoPrevio: 28735,
  avisoPrevio: 'VINTEDIAS',
  situacaoDemissao: 'GERADO',
  tipoDemissao: 'JUSTACAUSA',
};

export const sampleWithNewData: NewDemissaoFuncionario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
