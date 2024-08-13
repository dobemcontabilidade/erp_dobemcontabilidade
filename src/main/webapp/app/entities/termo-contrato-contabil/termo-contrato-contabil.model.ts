import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface ITermoContratoContabil {
  id: number;
  linkTermo?: string | null;
  descricao?: string | null;
  titulo?: string | null;
  planoContabil?: IPlanoContabil | null;
}

export type NewTermoContratoContabil = Omit<ITermoContratoContabil, 'id'> & { id: null };
