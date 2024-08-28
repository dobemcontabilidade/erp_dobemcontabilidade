import { IContador, NewContador } from './contador.model';

export const sampleWithRequiredData: IContador = {
  id: 21331,
  nome: 'more original inasmuch',
  cpf: 'watery rusty whispered',
  rg: 'back stick',
  sexo: 'MASCULINO',
  crc: 'ethernet lazily',
};

export const sampleWithPartialData: IContador = {
  id: 2286,
  nome: 'shareholder medicate',
  cpf: 'inside slowly',
  dataNascimento: 'curiously',
  rg: 'prime',
  rgOrgaoExpeditor: 'validate',
  localNascimento: 'wherever upwardly quizzical',
  racaECor: 'NAOINFORMADO',
  pessoaComDeficiencia: 'REABILITADO',
  sexo: 'FEMININO',
  urlFotoPerfil: 'edge',
  crc: 'circa stupefy beatboxer',
  limiteEmpresas: 28944,
  limiteAreaContabils: 31131,
  limiteDepartamentos: 28664,
};

export const sampleWithFullData: IContador = {
  id: 12361,
  nome: 'misname artificer',
  cpf: 'ouch hopelessly as',
  dataNascimento: 'versus busily',
  tituloEleitor: 10438,
  rg: 'anxiously ',
  rgOrgaoExpeditor: 'meanwhile',
  rgUfExpedicao: 'after',
  nomeMae: 'incidentally',
  nomePai: 'mineral tailor indeed',
  localNascimento: 'retouching',
  racaECor: 'PARDO',
  pessoaComDeficiencia: 'NAOPOSSUI',
  estadoCivil: 'VIUVO',
  sexo: 'FEMININO',
  urlFotoPerfil: 'mortified',
  rgOrgaoExpditor: 'whose geez bottleful',
  crc: 'tall',
  limiteEmpresas: 23365,
  limiteAreaContabils: 17615,
  limiteFaturamento: 27891.83,
  limiteDepartamentos: 3485,
  situacaoContador: 'HABILITADO',
};

export const sampleWithNewData: NewContador = {
  nome: 'blah',
  cpf: 'picturesque consequently a',
  rg: 'huzzah',
  sexo: 'FEMININO',
  crc: 'expert boohoo',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
