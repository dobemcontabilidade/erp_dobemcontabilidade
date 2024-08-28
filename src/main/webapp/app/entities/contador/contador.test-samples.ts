import { IContador, NewContador } from './contador.model';

export const sampleWithRequiredData: IContador = {
  id: 11817,
  nome: 'crafty',
  cpf: 'than huzzah arrogantly',
  rg: 'powerfully',
  sexo: 'MASCULINO',
  crc: 'ah',
};

export const sampleWithPartialData: IContador = {
  id: 16744,
  nome: 'since acidic considerate',
  cpf: 'usually and',
  dataNascimento: 'rapidly digital',
  tituloEleitor: 15802,
  rg: 'youthful',
  rgUfExpedicao: 'skateboard boo barring',
  nomePai: 'hmph block',
  racaECor: 'BRANCO',
  estadoCivil: 'SOLTERO',
  sexo: 'FEMININO',
  crc: 'uh-huh churn',
  limiteEmpresas: 20409,
  limiteFaturamento: 23795.84,
  limiteDepartamentos: 3467,
  situacaoContador: 'HABILITADO',
};

export const sampleWithFullData: IContador = {
  id: 23560,
  nome: 'bestseller whether snappy',
  cpf: 'unused minimalism',
  dataNascimento: 'whoa',
  tituloEleitor: 4643,
  rg: 'caper',
  rgOrgaoExpeditor: 'sanctity parade stealthily',
  rgUfExpedicao: 'rigidly closely gee',
  nomeMae: 'liquid tugboat after',
  nomePai: 'versus receive',
  localNascimento: 'nervous blizzard vice',
  racaECor: 'BRANCO',
  pessoaComDeficiencia: 'DEFICIENCIAVISUAL',
  estadoCivil: 'VIUVO',
  sexo: 'MASCULINO',
  urlFotoPerfil: 'luminous innocently midst',
  rgOrgaoExpditor: 'gee interior geez',
  crc: 'spend hasty',
  limiteEmpresas: 24304,
  limiteAreaContabils: 28146,
  limiteFaturamento: 3029.83,
  limiteDepartamentos: 26561,
  situacaoContador: 'PENDENTE',
};

export const sampleWithNewData: NewContador = {
  nome: 'hyphenation fondly finally',
  cpf: 'imitation between drug',
  rg: 'finally th',
  sexo: 'MASCULINO',
  crc: 'truck',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
