import { ITarefa } from 'app/entities/tarefa/tarefa.model';

export interface IDocumentoTarefa {
  id: number;
  nome?: string | null;
  tarefa?: Pick<ITarefa, 'id' | 'titulo'> | null;
}

export type NewDocumentoTarefa = Omit<IDocumentoTarefa, 'id'> & { id: null };
