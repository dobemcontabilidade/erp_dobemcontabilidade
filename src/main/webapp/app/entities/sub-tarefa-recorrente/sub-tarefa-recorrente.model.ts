import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';

export interface ISubTarefaRecorrente {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  ordem?: number | null;
  concluida?: boolean | null;
  tarefaRecorrenteExecucao?: ITarefaRecorrenteExecucao | null;
}

export type NewSubTarefaRecorrente = Omit<ISubTarefaRecorrente, 'id'> & { id: null };
