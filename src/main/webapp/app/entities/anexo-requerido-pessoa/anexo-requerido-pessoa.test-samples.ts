import { IAnexoRequeridoPessoa, NewAnexoRequeridoPessoa } from './anexo-requerido-pessoa.model';

export const sampleWithRequiredData: IAnexoRequeridoPessoa = {
  id: 3823,
};

export const sampleWithPartialData: IAnexoRequeridoPessoa = {
  id: 21191,
  tipo: 'CONTADOR',
};

export const sampleWithFullData: IAnexoRequeridoPessoa = {
  id: 25451,
  obrigatorio: false,
  tipo: 'CONTADOR',
};

export const sampleWithNewData: NewAnexoRequeridoPessoa = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
