import dayjs from 'dayjs/esm';

import { IPessoaFisica, NewPessoaFisica } from './pessoa-fisica.model';

export const sampleWithRequiredData: IPessoaFisica = {
  id: 29033,
  nome: 'indeed meanwhile',
  cpf: 'though pfft',
  rg: 'networking',
  sexo: 'FEMININO',
};

export const sampleWithPartialData: IPessoaFisica = {
  id: 19940,
  nome: 'forenenst vanadyl',
  cpf: 'beyond',
  dataNascimento: dayjs('2024-08-28T13:32'),
  tituloEleitor: 'prepone',
  rg: 'boo slowly enormously',
  rgOrgaoExpditor: 'deliberately',
  sexo: 'MASCULINO',
  urlFotoPerfil: 'snowmobiling',
};

export const sampleWithFullData: IPessoaFisica = {
  id: 1142,
  nome: 'viciously',
  cpf: 'sans',
  dataNascimento: dayjs('2024-08-28T09:28'),
  tituloEleitor: 'famous',
  rg: 'boo',
  rgOrgaoExpditor: 'amongst questionably grandchild',
  rgUfExpedicao: 'counterbalance across',
  estadoCivil: 'VIUVO',
  sexo: 'FEMININO',
  urlFotoPerfil: 'quiche',
};

export const sampleWithNewData: NewPessoaFisica = {
  nome: 'ape clogs',
  cpf: 'impure sedately',
  rg: 'concerning whenever',
  sexo: 'MASCULINO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
