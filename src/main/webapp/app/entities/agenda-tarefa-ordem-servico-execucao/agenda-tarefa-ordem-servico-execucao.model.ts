import dayjs from 'dayjs/esm';
import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';

export interface IAgendaTarefaOrdemServicoExecucao {
  id: number;
  horaInicio?: dayjs.Dayjs | null;
  horaFim?: dayjs.Dayjs | null;
  diaInteiro?: boolean | null;
  ativo?: boolean | null;
  tarefaOrdemServicoExecucao?: ITarefaOrdemServicoExecucao | null;
}

export type NewAgendaTarefaOrdemServicoExecucao = Omit<IAgendaTarefaOrdemServicoExecucao, 'id'> & { id: null };
