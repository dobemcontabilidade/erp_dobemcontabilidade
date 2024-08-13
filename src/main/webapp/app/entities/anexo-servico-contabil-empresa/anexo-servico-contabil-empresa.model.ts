import dayjs from 'dayjs/esm';
import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { IServicoContabilAssinaturaEmpresa } from 'app/entities/servico-contabil-assinatura-empresa/servico-contabil-assinatura-empresa.model';

export interface IAnexoServicoContabilEmpresa {
  id: number;
  link?: string | null;
  dataHoraUpload?: dayjs.Dayjs | null;
  anexoRequerido?: IAnexoRequerido | null;
  servicoContabilAssinaturaEmpresa?: IServicoContabilAssinaturaEmpresa | null;
}

export type NewAnexoServicoContabilEmpresa = Omit<IAnexoServicoContabilEmpresa, 'id'> & { id: null };
