import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface IOpcaoNomeFantasiaEmpresa {
  id: number;
  nome?: string | null;
  ordem?: number | null;
  selecionado?: boolean | null;
  empresa?: IEmpresa | null;
}

export type NewOpcaoNomeFantasiaEmpresa = Omit<IOpcaoNomeFantasiaEmpresa, 'id'> & { id: null };
