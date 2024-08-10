import { IEmpresa } from 'app/entities/empresa/empresa.model';

export interface IOpcaoRazaoSocialEmpresa {
  id: number;
  nome?: string | null;
  ordem?: number | null;
  selecionado?: boolean | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
}

export type NewOpcaoRazaoSocialEmpresa = Omit<IOpcaoRazaoSocialEmpresa, 'id'> & { id: null };
