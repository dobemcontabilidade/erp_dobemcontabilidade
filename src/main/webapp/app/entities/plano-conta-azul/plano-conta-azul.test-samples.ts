import { IPlanoContaAzul, NewPlanoContaAzul } from './plano-conta-azul.model';

export const sampleWithRequiredData: IPlanoContaAzul = {
  id: 22215,
};

export const sampleWithPartialData: IPlanoContaAzul = {
  id: 18565,
  nome: 'gee vice',
  valorBase: 22687.28,
  notaFiscalProduto: 32018,
  notaFiscalServico: 13505,
  notaFiscalCe: 13934,
};

export const sampleWithFullData: IPlanoContaAzul = {
  id: 29644,
  nome: 'meh tremendously zero',
  valorBase: 19820.16,
  usuarios: 29558,
  boletos: 24058,
  notaFiscalProduto: 27909,
  notaFiscalServico: 27381,
  notaFiscalCe: 1986,
  suporte: false,
  situacao: 'EXCLUIDO',
};

export const sampleWithNewData: NewPlanoContaAzul = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
