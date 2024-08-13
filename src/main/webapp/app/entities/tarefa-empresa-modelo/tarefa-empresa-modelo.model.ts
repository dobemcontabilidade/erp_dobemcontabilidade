import dayjs from 'dayjs/esm';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';

export interface ITarefaEmpresaModelo {
  id: number;
  dataAdmin?: dayjs.Dayjs | null;
  dataLegal?: dayjs.Dayjs | null;
  empresaModelo?: IEmpresaModelo | null;
  servicoContabil?: IServicoContabil | null;
}

export type NewTarefaEmpresaModelo = Omit<ITarefaEmpresaModelo, 'id'> & { id: null };
