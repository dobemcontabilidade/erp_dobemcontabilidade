import dayjs from 'dayjs/esm';

import { IPessoa, NewPessoa } from './pessoa.model';

export const sampleWithRequiredData: IPessoa = {
  id: 14636,
  nome: 'eek before woot',
  cpf: 'helplessly meh',
  rg: 'loud',
  sexo: 'MASCULINO',
};

export const sampleWithPartialData: IPessoa = {
  id: 26981,
  nome: 'phooey how',
  cpf: 'dearly ashamed gadzooks',
  dataNascimento: dayjs('2024-08-09T16:53'),
  rg: 'psst',
  rgOrgaoExpditor: 'underneath',
  rgUfExpedicao: 'sinful awesome for',
  sexo: 'FEMININO',
  urlFotoPerfil: 'energetically following',
};

export const sampleWithFullData: IPessoa = {
  id: 13942,
  nome: 'teleport upside-down cede',
  cpf: 'accurate provided uh-huh',
  dataNascimento: dayjs('2024-08-09T16:55'),
  tituloEleitor: 'expert cabinet',
  rg: 'display yowza',
  rgOrgaoExpditor: 'nettle rejuvenate',
  rgUfExpedicao: 'unless',
  estadoCivil: 'UNIAO_ESTAVEL',
  sexo: 'FEMININO',
  urlFotoPerfil: 'bellows as coolly',
};

export const sampleWithNewData: NewPessoa = {
  nome: 'well-worn sentimental',
  cpf: 'swiftly',
  rg: 'watery malfunction',
  sexo: 'FEMININO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
