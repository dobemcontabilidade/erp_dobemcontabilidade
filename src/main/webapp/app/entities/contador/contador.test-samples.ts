import { IContador, NewContador } from './contador.model';

export const sampleWithRequiredData: IContador = {
  id: 4926,
  nome: 'package upliftingly',
  cpf: 'although or and',
  rg: 'powerfully',
  sexo: 'FEMININO',
  crc: 'beyond',
};

export const sampleWithPartialData: IContador = {
  id: 17033,
  nome: 'sore nuke',
  cpf: 'afore phone though',
  rg: 'dry profes',
  rgOrgaoExpeditor: 'eggnog warped swerve',
  nomeMae: 'wherever',
  localNascimento: 'fatherly hmph',
  sexo: 'FEMININO',
  crc: 'wherever boohoo loftily',
  limiteEmpresas: 13009,
  limiteAreaContabils: 6221,
};

export const sampleWithFullData: IContador = {
  id: 25424,
  nome: 'offer',
  cpf: 'disgusting nervously',
  dataNascimento: 'aw atop underch',
  tituloEleitor: 10488,
  rg: 'rip',
  rgOrgaoExpeditor: 'among',
  rgUfExpedicao: 'following',
  nomeMae: 'encash anteater',
  nomePai: 'or',
  localNascimento: 'whoa',
  racaECor: 'NEGRO',
  pessoaComDeficiencia: 'DEFICIENCIAAUDITIVA',
  estadoCivil: 'UNIAOESTAVEL',
  sexo: 'MASCULINO',
  urlFotoPerfil: 'besides',
  rgOrgaoExpditor: 'reunite fooey except',
  crc: 'or notwithstanding mmm',
  limiteEmpresas: 8003,
  limiteAreaContabils: 15971,
  limiteFaturamento: 9370.77,
  limiteDepartamentos: 32660,
  situacaoContador: 'HABILITADO',
};

export const sampleWithNewData: NewContador = {
  nome: 'noisily',
  cpf: 'creaking courageously pike',
  rg: 'dome oats ',
  sexo: 'FEMININO',
  crc: 'often',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
