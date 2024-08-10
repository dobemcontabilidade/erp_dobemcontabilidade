import { IContador } from 'app/entities/contador/contador.model';
import { IAvaliacao } from 'app/entities/avaliacao/avaliacao.model';

export interface IAvaliacaoContador {
  id: number;
  pontuacao?: number | null;
  contador?: Pick<IContador, 'id' | 'nome'> | null;
  avaliacao?: Pick<IAvaliacao, 'id' | 'nome'> | null;
}

export type NewAvaliacaoContador = Omit<IAvaliacaoContador, 'id'> & { id: null };
