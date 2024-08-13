import dayjs from 'dayjs/esm';
import { ITarefaOrdemServicoExecucao } from 'app/entities/tarefa-ordem-servico-execucao/tarefa-ordem-servico-execucao.model';
import { IContador } from 'app/entities/contador/contador.model';

export interface IContadorResponsavelOrdemServico {
  id: number;
  dataAtribuicao?: dayjs.Dayjs | null;
  dataRevogacao?: dayjs.Dayjs | null;
  tarefaOrdemServicoExecucao?: ITarefaOrdemServicoExecucao | null;
  contador?: IContador | null;
}

export type NewContadorResponsavelOrdemServico = Omit<IContadorResponsavelOrdemServico, 'id'> & { id: null };
