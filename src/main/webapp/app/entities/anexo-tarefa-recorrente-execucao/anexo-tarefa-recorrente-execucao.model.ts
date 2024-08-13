import dayjs from 'dayjs/esm';
import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';

export interface IAnexoTarefaRecorrenteExecucao {
  id: number;
  url?: string | null;
  descricao?: string | null;
  dataHoraUpload?: dayjs.Dayjs | null;
  tarefaRecorrenteExecucao?: ITarefaRecorrenteExecucao | null;
}

export type NewAnexoTarefaRecorrenteExecucao = Omit<IAnexoTarefaRecorrenteExecucao, 'id'> & { id: null };
