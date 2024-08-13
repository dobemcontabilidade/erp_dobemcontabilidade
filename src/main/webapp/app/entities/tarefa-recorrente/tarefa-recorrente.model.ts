import dayjs from 'dayjs/esm';
import { IServicoContabilAssinaturaEmpresa } from 'app/entities/servico-contabil-assinatura-empresa/servico-contabil-assinatura-empresa.model';
import { TipoRecorrenciaEnum } from 'app/entities/enumerations/tipo-recorrencia-enum.model';

export interface ITarefaRecorrente {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  notificarCliente?: boolean | null;
  notificarContador?: boolean | null;
  anoReferencia?: number | null;
  dataAdmin?: dayjs.Dayjs | null;
  recorencia?: keyof typeof TipoRecorrenciaEnum | null;
  servicoContabilAssinaturaEmpresa?: IServicoContabilAssinaturaEmpresa | null;
}

export type NewTarefaRecorrente = Omit<ITarefaRecorrente, 'id'> & { id: null };
