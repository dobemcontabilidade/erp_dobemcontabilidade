import { ISubclasseCnae } from 'app/entities/subclasse-cnae/subclasse-cnae.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface IAtividadeEmpresa {
  id: number;
  principal?: boolean | null;
  ordem?: number | null;
  descricaoAtividade?: string | null;
  cnae?: Pick<ISubclasseCnae, 'id' | 'descricao'> | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
}

export type NewAtividadeEmpresa = Omit<IAtividadeEmpresa, 'id'> & { id: null };
