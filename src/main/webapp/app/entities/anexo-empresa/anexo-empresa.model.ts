import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface IAnexoEmpresa {
  id: number;
  urlAnexo?: string | null;
  tipo?: string | null;
  descricao?: string | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
}

export type NewAnexoEmpresa = Omit<IAnexoEmpresa, 'id'> & { id: null };
