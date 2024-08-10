import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface ITermoContratoContabil {
  id: number;
  documento?: string | null;
  descricao?: string | null;
  titulo?: string | null;
  planoContabil?: Pick<IPlanoContabil, 'id' | 'nome'> | null;
}

export type NewTermoContratoContabil = Omit<ITermoContratoContabil, 'id'> & { id: null };
