import dayjs from 'dayjs/esm';
import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';

export interface IAgendaTarefaRecorrenteExecucao {
  id: number;
  ativo?: boolean | null;
  horaInicio?: dayjs.Dayjs | null;
  horaFim?: dayjs.Dayjs | null;
  diaInteiro?: boolean | null;
  comentario?: string | null;
  tarefaRecorrenteExecucao?: ITarefaRecorrenteExecucao | null;
}

export type NewAgendaTarefaRecorrenteExecucao = Omit<IAgendaTarefaRecorrenteExecucao, 'id'> & { id: null };
