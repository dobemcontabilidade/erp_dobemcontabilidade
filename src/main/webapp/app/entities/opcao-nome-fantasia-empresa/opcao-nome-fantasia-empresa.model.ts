import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface IOpcaoNomeFantasiaEmpresa {
  id: number;
  nome?: string | null;
  ordem?: number | null;
  selecionado?: boolean | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
}

export type NewOpcaoNomeFantasiaEmpresa = Omit<IOpcaoNomeFantasiaEmpresa, 'id'> & { id: null };
