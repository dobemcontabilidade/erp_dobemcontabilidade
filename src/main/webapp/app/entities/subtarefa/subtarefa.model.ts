import { ITarefa } from 'app/entities/tarefa/tarefa.model';

export interface ISubtarefa {
  id: number;
  ordem?: number | null;
  item?: string | null;
  descricao?: string | null;
  tarefa?: ITarefa | null;
}

export type NewSubtarefa = Omit<ISubtarefa, 'id'> & { id: null };
