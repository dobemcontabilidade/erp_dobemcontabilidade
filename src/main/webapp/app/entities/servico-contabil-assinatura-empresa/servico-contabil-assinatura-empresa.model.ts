import dayjs from 'dayjs/esm';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';

export interface IServicoContabilAssinaturaEmpresa {
  id: number;
  dataLegal?: dayjs.Dayjs | null;
  dataAdmin?: dayjs.Dayjs | null;
  servicoContabil?: IServicoContabil | null;
  assinaturaEmpresa?: IAssinaturaEmpresa | null;
}

export type NewServicoContabilAssinaturaEmpresa = Omit<IServicoContabilAssinaturaEmpresa, 'id'> & { id: null };
