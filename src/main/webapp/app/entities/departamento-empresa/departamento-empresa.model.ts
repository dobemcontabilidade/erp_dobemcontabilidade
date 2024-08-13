import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IContador } from 'app/entities/contador/contador.model';

export interface IDepartamentoEmpresa {
  id: number;
  pontuacao?: number | null;
  depoimento?: string | null;
  reclamacao?: string | null;
  departamento?: IDepartamento | null;
  empresa?: IEmpresa | null;
  contador?: IContador | null;
}

export type NewDepartamentoEmpresa = Omit<IDepartamentoEmpresa, 'id'> & { id: null };
