import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';

export interface IAnexoRequeridoEmpresa {
  id: number;
  obrigatorio?: boolean | null;
  urlArquivo?: string | null;
  anexoRequerido?: IAnexoRequerido | null;
  enquadramento?: IEnquadramento | null;
  tributacao?: ITributacao | null;
  ramo?: IRamo | null;
  empresa?: IEmpresa | null;
  empresaModelo?: IEmpresaModelo | null;
}

export type NewAnexoRequeridoEmpresa = Omit<IAnexoRequeridoEmpresa, 'id'> & { id: null };
