import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IContador } from 'app/entities/contador/contador.model';

export interface IDepartamentoEmpresa {
  id: number;
  pontuacao?: number | null;
  depoimento?: string | null;
  reclamacao?: string | null;
  departamento?: Pick<IDepartamento, 'id' | 'nome'> | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
  contador?: Pick<IContador, 'id' | 'nome'> | null;
}

export type NewDepartamentoEmpresa = Omit<IDepartamentoEmpresa, 'id'> & { id: null };
