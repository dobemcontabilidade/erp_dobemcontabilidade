import { IContratoFuncionario, NewContratoFuncionario } from './contrato-funcionario.model';

export const sampleWithRequiredData: IContratoFuncionario = {
  id: 18990,
};

export const sampleWithPartialData: IContratoFuncionario = {
  id: 8045,
  salarioFixo: false,
  estagio: true,
  orgaoEmissorDocumento: 'per why',
  dataValidadeDocumento: 'deliberately',
  dataAdmissao: 'cheerfully useful',
  descricaoAtividades: '../fake-data/blob/hipster.txt',
  datainicioContrato: 'gadzooks',
  horasATrabalhadar: 22461,
  codigoCargo: 'bundle unh',
  categoriaTrabalhador: 'EMPREGADO',
  tipoVinculoTrabalho: 'RURAL',
  tIpoDocumentoEnum: 'CNH',
  periodoExperiencia: 'QUINZE',
  tipoAdmisaoEnum: 'SUCESSAO',
  periodoIntermitente: 'UMANOSEISMESES',
  indicativoAdmissao: 'ACAOFISCAL',
  numeroPisNisPasep: 546,
};

export const sampleWithFullData: IContratoFuncionario = {
  id: 31525,
  salarioFixo: false,
  salarioVariavel: true,
  estagio: true,
  naturezaEstagioEnum: 'OBRIGATORIO',
  ctps: 'more omission',
  serieCtps: 11516,
  orgaoEmissorDocumento: 'usually',
  dataValidadeDocumento: 'impeach for',
  dataAdmissao: 'even soon yippee',
  cargo: 'ask immunize yippee',
  descricaoAtividades: '../fake-data/blob/hipster.txt',
  situacao: 'ADMITIDO',
  valorSalarioFixo: 'nobody sans',
  valorSalarioVariavel: 'gently oh dearest',
  dataTerminoContrato: 'convert',
  datainicioContrato: 'per between',
  horasATrabalhadar: 3885,
  codigoCargo: 'wiretap',
  categoriaTrabalhador: 'ESTAGIARIO',
  tipoVinculoTrabalho: 'URBANO',
  fgtsOpcao: 'NAOOPTANTE',
  tIpoDocumentoEnum: 'OC',
  periodoExperiencia: 'SESENTA',
  tipoAdmisaoEnum: 'FUSAO',
  periodoIntermitente: 'CINCOMESES',
  indicativoAdmissao: 'DECISAOJUDICIAL',
  numeroPisNisPasep: 11859,
};

export const sampleWithNewData: NewContratoFuncionario = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
