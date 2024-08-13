import { IContador } from 'app/entities/contador/contador.model';
import { IAvaliacao } from 'app/entities/avaliacao/avaliacao.model';

export interface IAvaliacaoContador {
  id: number;
  pontuacao?: number | null;
  contador?: IContador | null;
  avaliacao?: IAvaliacao | null;
}

export type NewAvaliacaoContador = Omit<IAvaliacaoContador, 'id'> & { id: null };
