import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { ITarefaRecorrente } from 'app/entities/tarefa-recorrente/tarefa-recorrente.model';

export interface IAnexoRequeridoTarefaRecorrente {
  id: number;
  obrigatorio?: boolean | null;
  anexoRequerido?: IAnexoRequerido | null;
  tarefaRecorrente?: ITarefaRecorrente | null;
}

export type NewAnexoRequeridoTarefaRecorrente = Omit<IAnexoRequeridoTarefaRecorrente, 'id'> & { id: null };
