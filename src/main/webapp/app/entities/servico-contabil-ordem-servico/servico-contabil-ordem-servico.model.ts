import dayjs from 'dayjs/esm';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';

export interface IServicoContabilOrdemServico {
  id: number;
  dataAdmin?: dayjs.Dayjs | null;
  dataLegal?: dayjs.Dayjs | null;
  servicoContabil?: IServicoContabil | null;
  ordemServico?: IOrdemServico | null;
}

export type NewServicoContabilOrdemServico = Omit<IServicoContabilOrdemServico, 'id'> & { id: null };
