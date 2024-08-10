import { IPlanoContaAzul, NewPlanoContaAzul } from './plano-conta-azul.model';

export const sampleWithRequiredData: IPlanoContaAzul = {
  id: 25617,
};

export const sampleWithPartialData: IPlanoContaAzul = {
  id: 8544,
  nome: 'primate',
  valorBase: 4671.31,
  usuarios: 5790,
  notaFiscalServico: 29339,
  suporte: false,
  situacao: 'INATIVO',
};

export const sampleWithFullData: IPlanoContaAzul = {
  id: 7192,
  nome: 'outstanding until',
  valorBase: 26815.06,
  usuarios: 17409,
  boletos: 12306,
  notaFiscalProduto: 3170,
  notaFiscalServico: 17314,
  notaFiscalCe: 19452,
  suporte: true,
  situacao: 'ATIVO',
};

export const sampleWithNewData: NewPlanoContaAzul = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
