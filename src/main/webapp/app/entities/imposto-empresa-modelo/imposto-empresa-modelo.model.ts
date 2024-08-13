import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { IImposto } from 'app/entities/imposto/imposto.model';

export interface IImpostoEmpresaModelo {
  id: number;
  nome?: string | null;
  observacao?: string | null;
  empresaModelo?: IEmpresaModelo | null;
  imposto?: IImposto | null;
}

export type NewImpostoEmpresaModelo = Omit<IImpostoEmpresaModelo, 'id'> & { id: null };
