import dayjs from 'dayjs/esm';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { SituacaoUsuarioEmpresaEnum } from 'app/entities/enumerations/situacao-usuario-empresa-enum.model';
import { TipoUsuarioEmpresaEnum } from 'app/entities/enumerations/tipo-usuario-empresa-enum.model';

export interface IUsuarioEmpresa {
  id: number;
  email?: string | null;
  senha?: string | null;
  token?: string | null;
  ativo?: boolean | null;
  dataHoraAtivacao?: dayjs.Dayjs | null;
  dataLimiteAcesso?: dayjs.Dayjs | null;
  situacaoUsuarioEmpresa?: keyof typeof SituacaoUsuarioEmpresaEnum | null;
  tipoUsuarioEmpresaEnum?: keyof typeof TipoUsuarioEmpresaEnum | null;
  assinaturaEmpresa?: IAssinaturaEmpresa | null;
}

export type NewUsuarioEmpresa = Omit<IUsuarioEmpresa, 'id'> & { id: null };
