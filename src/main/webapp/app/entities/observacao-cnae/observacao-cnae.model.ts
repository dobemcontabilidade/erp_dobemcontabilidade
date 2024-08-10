import { ISubclasseCnae } from 'app/entities/subclasse-cnae/subclasse-cnae.model';

export interface IObservacaoCnae {
  id: number;
  descricao?: string | null;
  subclasse?: Pick<ISubclasseCnae, 'id' | 'descricao'> | null;
}

export type NewObservacaoCnae = Omit<IObservacaoCnae, 'id'> & { id: null };
