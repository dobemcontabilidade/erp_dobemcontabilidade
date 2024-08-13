import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';

export interface IServicoContabilEmpresaModelo {
  id: number;
  obrigatorio?: boolean | null;
  empresaModelo?: IEmpresaModelo | null;
  servicoContabil?: IServicoContabil | null;
}

export type NewServicoContabilEmpresaModelo = Omit<IServicoContabilEmpresaModelo, 'id'> & { id: null };
