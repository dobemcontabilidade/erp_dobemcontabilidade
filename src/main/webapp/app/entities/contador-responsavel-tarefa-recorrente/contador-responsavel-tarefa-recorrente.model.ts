import dayjs from 'dayjs/esm';
import { ITarefaRecorrenteExecucao } from 'app/entities/tarefa-recorrente-execucao/tarefa-recorrente-execucao.model';
import { IContador } from 'app/entities/contador/contador.model';

export interface IContadorResponsavelTarefaRecorrente {
  id: number;
  dataAtribuicao?: dayjs.Dayjs | null;
  dataRevogacao?: dayjs.Dayjs | null;
  concluida?: boolean | null;
  tarefaRecorrenteExecucao?: ITarefaRecorrenteExecucao | null;
  contador?: IContador | null;
}

export type NewContadorResponsavelTarefaRecorrente = Omit<IContadorResponsavelTarefaRecorrente, 'id'> & { id: null };
