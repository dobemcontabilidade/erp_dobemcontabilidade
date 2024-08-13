import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';

export interface IGrupoAcessoEmpresa {
  id: number;
  nome?: string | null;
  assinaturaEmpresa?: IAssinaturaEmpresa | null;
}

export type NewGrupoAcessoEmpresa = Omit<IGrupoAcessoEmpresa, 'id'> & { id: null };
