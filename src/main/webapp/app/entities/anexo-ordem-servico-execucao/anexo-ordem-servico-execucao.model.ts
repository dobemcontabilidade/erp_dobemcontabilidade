import dayjs from 'dayjs/esm';
import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';

export interface IAnexoOrdemServicoExecucao {
  id: number;
  url?: string | null;
  descricao?: string | null;
  dataHoraUpload?: dayjs.Dayjs | null;
  tarefaOrdemServicoExecucao?: ITarefaOrdemServicoExecucao | null;
}

export type NewAnexoOrdemServicoExecucao = Omit<IAnexoOrdemServicoExecucao, 'id'> & { id: null };
