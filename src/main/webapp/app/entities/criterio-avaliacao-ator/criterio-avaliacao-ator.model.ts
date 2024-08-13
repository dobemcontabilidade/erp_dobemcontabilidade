import { ICriterioAvaliacao } from 'app/entities/criterio-avaliacao/criterio-avaliacao.model';
import { IAtorAvaliado } from 'app/entities/ator-avaliado/ator-avaliado.model';

export interface ICriterioAvaliacaoAtor {
  id: number;
  descricao?: string | null;
  ativo?: boolean | null;
  criterioAvaliacao?: ICriterioAvaliacao | null;
  atorAvaliado?: IAtorAvaliado | null;
}

export type NewCriterioAvaliacaoAtor = Omit<ICriterioAvaliacaoAtor, 'id'> & { id: null };
