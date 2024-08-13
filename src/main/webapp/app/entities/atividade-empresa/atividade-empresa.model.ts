import { ISubclasseCnae } from 'app/entities/subclasse-cnae/subclasse-cnae.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface IAtividadeEmpresa {
  id: number;
  principal?: boolean | null;
  ordem?: number | null;
  descricaoAtividade?: string | null;
  cnae?: ISubclasseCnae | null;
  empresa?: IEmpresa | null;
}

export type NewAtividadeEmpresa = Omit<IAtividadeEmpresa, 'id'> & { id: null };
