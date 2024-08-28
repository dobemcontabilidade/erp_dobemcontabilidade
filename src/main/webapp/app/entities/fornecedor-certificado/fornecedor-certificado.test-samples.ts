import { IFornecedorCertificado, NewFornecedorCertificado } from './fornecedor-certificado.model';

export const sampleWithRequiredData: IFornecedorCertificado = {
  id: 22584,
  razaoSocial: 'thumb ugh',
};

export const sampleWithPartialData: IFornecedorCertificado = {
  id: 6112,
  razaoSocial: 'yippee gladly whether',
};

export const sampleWithFullData: IFornecedorCertificado = {
  id: 10059,
  razaoSocial: 'through plasticize',
  sigla: 'and',
  descricao: '../fake-data/blob/hipster.txt',
};

export const sampleWithNewData: NewFornecedorCertificado = {
  razaoSocial: 'peaceful',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
