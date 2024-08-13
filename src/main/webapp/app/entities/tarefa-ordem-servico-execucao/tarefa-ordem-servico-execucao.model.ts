import dayjs from 'dayjs/esm';
import { ITarefaOrdemServico } from 'app/entities/tarefa-ordem-servico/tarefa-ordem-servico.model';
import { MesCompetenciaEnum } from 'app/entities/enumerations/mes-competencia-enum.model';
import { SituacaoTarefaEnum } from 'app/entities/enumerations/situacao-tarefa-enum.model';

export interface ITarefaOrdemServicoExecucao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  ordem?: number | null;
  dataEntrega?: dayjs.Dayjs | null;
  dataAgendada?: dayjs.Dayjs | null;
  concluida?: boolean | null;
  notificarCliente?: boolean | null;
  mes?: keyof typeof MesCompetenciaEnum | null;
  situacaoTarefa?: keyof typeof SituacaoTarefaEnum | null;
  tarefaOrdemServico?: ITarefaOrdemServico | null;
}

export type NewTarefaOrdemServicoExecucao = Omit<ITarefaOrdemServicoExecucao, 'id'> & { id: null };
