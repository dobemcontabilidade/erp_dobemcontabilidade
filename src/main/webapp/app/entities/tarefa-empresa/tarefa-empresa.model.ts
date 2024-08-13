import dayjs from 'dayjs/esm';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IContador } from 'app/entities/contador/contador.model';
import { ITarefa } from 'app/entities/tarefa/tarefa.model';

export interface ITarefaEmpresa {
  id: number;
  dataHora?: dayjs.Dayjs | null;
  empresa?: IEmpresa | null;
  contador?: IContador | null;
  tarefa?: ITarefa | null;
}

export type NewTarefaEmpresa = Omit<ITarefaEmpresa, 'id'> & { id: null };
