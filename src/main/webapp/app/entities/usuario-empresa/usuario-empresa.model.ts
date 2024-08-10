import dayjs from 'dayjs/esm';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { SituacaoUsuarioEmpresaEnum } from 'app/entities/enumerations/situacao-usuario-empresa-enum.model';

export interface IUsuarioEmpresa {
  id: number;
  email?: string | null;
  senha?: string | null;
  token?: string | null;
  dataHoraAtivacao?: dayjs.Dayjs | null;
  dataLimiteAcesso?: dayjs.Dayjs | null;
  situacao?: keyof typeof SituacaoUsuarioEmpresaEnum | null;
  pessoa?: Pick<IPessoa, 'id' | 'nome'> | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
}

export type NewUsuarioEmpresa = Omit<IUsuarioEmpresa, 'id'> & { id: null };
