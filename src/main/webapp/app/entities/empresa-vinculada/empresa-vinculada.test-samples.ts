import { IEmpresaVinculada, NewEmpresaVinculada } from './empresa-vinculada.model';

export const sampleWithRequiredData: IEmpresaVinculada = {
  id: 17952,
};

export const sampleWithPartialData: IEmpresaVinculada = {
  id: 25666,
  dataTerminoContrato: 'polenta wrist',
  descricaoComplementar: 'ego',
  regimePrevidenciario: 'RGPS',
  jornadaEspecial: 'NAOAPLICA',
  tipoRegimeTrabalho: 'CLT',
  tipoJornadaEmpresaVinculada: 'VARIAVEL',
};

export const sampleWithFullData: IEmpresaVinculada = {
  id: 23888,
  nomeEmpresa: 'layer pfft upon',
  cnpj: 'patiently',
  remuneracaoEmpresa: 'pigsty',
  observacoes: 'owlishly',
  salarioFixo: true,
  salarioVariavel: true,
  valorSalarioFixo: 'muddy',
  dataTerminoContrato: 'and',
  numeroInscricao: 23925,
  codigoLotacao: 17794,
  descricaoComplementar: 'portfolio psst',
  descricaoCargo: 'consequently realistic',
  observacaoJornadaTrabalho: 'recollection before outstanding',
  mediaHorasTrabalhadasSemana: 30923,
  regimePrevidenciario: 'RGPS',
  unidadePagamentoSalario: 'SEMANA',
  jornadaEspecial: 'NAOAPLICA',
  tipoInscricaoEmpresaVinculada: 'CNO',
  tipoContratoTrabalho: 'INDETERMINADO',
  tipoRegimeTrabalho: 'CLT',
  diasDaSemana: 'TERCAFEIRA',
  tipoJornadaEmpresaVinculada: 'VARIAVEL',
};

export const sampleWithNewData: NewEmpresaVinculada = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
