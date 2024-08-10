import { IContador } from 'app/entities/contador/contador.model';

export interface IAreaContabilEmpresa {
  id: number;
  pontuacao?: number | null;
  depoimento?: string | null;
  reclamacao?: string | null;
  contador?: Pick<IContador, 'id' | 'nome'> | null;
}

export type NewAreaContabilEmpresa = Omit<IAreaContabilEmpresa, 'id'> & { id: null };
