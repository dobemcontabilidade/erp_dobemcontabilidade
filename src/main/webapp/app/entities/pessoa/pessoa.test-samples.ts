import { IPessoa, NewPessoa } from './pessoa.model';

export const sampleWithRequiredData: IPessoa = {
  id: 31544,
  nome: 'provided',
  cpf: 'ouch',
  rg: 'snuggle',
  sexo: 'MASCULINO',
};

export const sampleWithPartialData: IPessoa = {
  id: 19426,
  nome: 'brr process',
  cpf: 'brilliant crew given',
  rg: 'unnaturall',
  rgUfExpedicao: 'surprised',
  nomeMae: 'ack ink',
  localNascimento: 'without',
  sexo: 'FEMININO',
};

export const sampleWithFullData: IPessoa = {
  id: 25280,
  nome: 'bale',
  cpf: 'angry dead',
  dataNascimento: 'onto',
  tituloEleitor: 28569,
  rg: 'handball',
  rgOrgaoExpeditor: 'blah quicker',
  rgUfExpedicao: 'without',
  nomeMae: 'likewise',
  nomePai: 'fortunately',
  localNascimento: 'required',
  racaECor: 'INDIGENA',
  pessoaComDeficiencia: 'DEFICIENCIAAUDITIVA',
  estadoCivil: 'VIUVO',
  sexo: 'FEMININO',
  urlFotoPerfil: 'kissingly',
};

export const sampleWithNewData: NewPessoa = {
  nome: 'amongst gatecrash meek',
  cpf: 'a',
  rg: 'control co',
  sexo: 'FEMININO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
