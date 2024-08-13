import { IEnderecoPessoa, NewEnderecoPessoa } from './endereco-pessoa.model';

export const sampleWithRequiredData: IEnderecoPessoa = {
  id: 20713,
};

export const sampleWithPartialData: IEnderecoPessoa = {
  id: 30444,
  complemento: 'rumble debtor exciting',
  bairro: 'patiently',
  cep: 'qua shril',
  principal: true,
  residenciaPropria: true,
};

export const sampleWithFullData: IEnderecoPessoa = {
  id: 23574,
  logradouro: 'steal within kiddingly',
  numero: 'aw wh',
  complemento: 'ack',
  bairro: 'as quiver let',
  cep: 'self-reli',
  principal: false,
  residenciaPropria: true,
};

export const sampleWithNewData: NewEnderecoPessoa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
