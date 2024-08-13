import dayjs from 'dayjs/esm';
import { IServicoContabilOrdemServico } from 'app/entities/servico-contabil-ordem-servico/servico-contabil-ordem-servico.model';

export interface ITarefaOrdemServico {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  notificarCliente?: boolean | null;
  notificarContador?: boolean | null;
  anoReferencia?: number | null;
  dataAdmin?: dayjs.Dayjs | null;
  servicoContabilOrdemServico?: IServicoContabilOrdemServico | null;
}

export type NewTarefaOrdemServico = Omit<ITarefaOrdemServico, 'id'> & { id: null };
