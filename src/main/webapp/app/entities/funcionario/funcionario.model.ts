import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { SituacaoFuncionarioEnum } from 'app/entities/enumerations/situacao-funcionario-enum.model';

export interface IFuncionario {
  id: number;
  nome?: string | null;
  salario?: number | null;
  ctps?: string | null;
  cargo?: string | null;
  descricaoAtividades?: string | null;
  situacao?: keyof typeof SituacaoFuncionarioEnum | null;
  pessoa?: Pick<IPessoa, 'id' | 'nome'> | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
}

export type NewFuncionario = Omit<IFuncionario, 'id'> & { id: null };
