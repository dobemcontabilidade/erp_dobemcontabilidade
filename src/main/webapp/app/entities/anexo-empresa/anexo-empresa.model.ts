import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IAnexoRequeridoEmpresa } from 'app/entities/anexo-requerido-empresa/anexo-requerido-empresa.model';

export interface IAnexoEmpresa {
  id: number;
  urlAnexo?: string | null;
  empresa?: IEmpresa | null;
  anexoRequeridoEmpresa?: IAnexoRequeridoEmpresa | null;
}

export type NewAnexoEmpresa = Omit<IAnexoEmpresa, 'id'> & { id: null };
