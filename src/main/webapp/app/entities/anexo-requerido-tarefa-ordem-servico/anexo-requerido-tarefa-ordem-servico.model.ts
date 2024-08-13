import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { ITarefaOrdemServico } from 'app/entities/tarefa-ordem-servico/tarefa-ordem-servico.model';

export interface IAnexoRequeridoTarefaOrdemServico {
  id: number;
  obrigatorio?: boolean | null;
  anexoRequerido?: IAnexoRequerido | null;
  tarefaOrdemServico?: ITarefaOrdemServico | null;
}

export type NewAnexoRequeridoTarefaOrdemServico = Omit<IAnexoRequeridoTarefaOrdemServico, 'id'> & { id: null };
