import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IProfissao } from 'app/entities/profissao/profissao.model';
import { TipoFuncionarioEnum } from 'app/entities/enumerations/tipo-funcionario-enum.model';

export interface IFuncionario {
  id: number;
  numeroPisNisPasep?: number | null;
  reintegrado?: boolean | null;
  primeiroEmprego?: boolean | null;
  multiploVinculos?: boolean | null;
  dataOpcaoFgts?: string | null;
  filiacaoSindical?: boolean | null;
  cnpjSindicato?: string | null;
  tipoFuncionarioEnum?: keyof typeof TipoFuncionarioEnum | null;
  usuarioEmpresa?: IUsuarioEmpresa | null;
  pessoa?: IPessoa | null;
  empresa?: IEmpresa | null;
  profissao?: IProfissao | null;
}

export type NewFuncionario = Omit<IFuncionario, 'id'> & { id: null };
