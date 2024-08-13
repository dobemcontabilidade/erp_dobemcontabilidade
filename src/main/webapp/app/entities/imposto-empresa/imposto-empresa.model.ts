import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IImposto } from 'app/entities/imposto/imposto.model';

export interface IImpostoEmpresa {
  id: number;
  diaVencimento?: number | null;
  empresa?: IEmpresa | null;
  imposto?: IImposto | null;
}

export type NewImpostoEmpresa = Omit<IImpostoEmpresa, 'id'> & { id: null };
