import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';

export interface ISubTarefaOrdemServico {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  ordem?: number | null;
  concluida?: boolean | null;
  tarefaOrdemServicoExecucao?: ITarefaOrdemServicoExecucao | null;
}

export type NewSubTarefaOrdemServico = Omit<ISubTarefaOrdemServico, 'id'> & { id: null };
