import dayjs from 'dayjs/esm';
import { ITarefaRecorrente } from 'app/entities/tarefa-recorrente/tarefa-recorrente.model';
import { MesCompetenciaEnum } from 'app/entities/enumerations/mes-competencia-enum.model';
import { SituacaoTarefaEnum } from 'app/entities/enumerations/situacao-tarefa-enum.model';

export interface ITarefaRecorrenteExecucao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  dataEntrega?: dayjs.Dayjs | null;
  dataAgendada?: dayjs.Dayjs | null;
  ordem?: number | null;
  concluida?: boolean | null;
  mes?: keyof typeof MesCompetenciaEnum | null;
  situacaoTarefa?: keyof typeof SituacaoTarefaEnum | null;
  tarefaRecorrente?: ITarefaRecorrente | null;
}

export type NewTarefaRecorrenteExecucao = Omit<ITarefaRecorrenteExecucao, 'id'> & { id: null };
