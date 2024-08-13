import { IAgendaTarefaRecorrenteExecucao } from 'app/entities/agenda-tarefa-recorrente-execucao/agenda-tarefa-recorrente-execucao.model';
import { IAgendaTarefaOrdemServicoExecucao } from 'app/entities/agenda-tarefa-ordem-servico-execucao/agenda-tarefa-ordem-servico-execucao.model';
import { TipoVisualizacaoAgendaEnum } from 'app/entities/enumerations/tipo-visualizacao-agenda-enum.model';

export interface IAgendaContadorConfig {
  id: number;
  ativo?: boolean | null;
  tipoVisualizacaoAgendaEnum?: keyof typeof TipoVisualizacaoAgendaEnum | null;
  agendaTarefaRecorrenteExecucao?: IAgendaTarefaRecorrenteExecucao | null;
  agendaTarefaOrdemServicoExecucao?: IAgendaTarefaOrdemServicoExecucao | null;
}

export type NewAgendaContadorConfig = Omit<IAgendaContadorConfig, 'id'> & { id: null };
