import dayjs from 'dayjs/esm';

import { IPessoa, NewPessoa } from './pessoa.model';

export const sampleWithRequiredData: IPessoa = {
  id: 2940,
  nome: 'automation',
  cpf: 'plead',
  rg: 'complicate foreigner',
  sexo: 'FEMININO',
};

export const sampleWithPartialData: IPessoa = {
  id: 9209,
  nome: 'instead for',
  cpf: 'daintily rage',
  dataNascimento: dayjs('2024-08-10T01:50'),
  tituloEleitor: 'plus',
  rg: 'beside along astride',
  rgOrgaoExpditor: 'beside',
  estadoCivil: 'DIVORCIADO',
  sexo: 'FEMININO',
};

export const sampleWithFullData: IPessoa = {
  id: 24026,
  nome: 'awesome for factory',
  cpf: 'whereas scrap',
  dataNascimento: dayjs('2024-08-09T18:36'),
  tituloEleitor: 'continually bah stub',
  rg: 'truly when suspicious',
  rgOrgaoExpditor: 'until pitch',
  rgUfExpedicao: 'finally yippee hm',
  estadoCivil: 'DIVORCIADO',
  sexo: 'MASCULINO',
  urlFotoPerfil: 'samurai past what',
};

export const sampleWithNewData: NewPessoa = {
  nome: 'covet ah political',
  cpf: 'vice whose',
  rg: 'sans inside extol',
  sexo: 'FEMININO',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
