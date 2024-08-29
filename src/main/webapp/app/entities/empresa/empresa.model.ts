import dayjs from 'dayjs/esm';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';

export interface IEmpresa {
  id: number;
  razaoSocial?: string | null;
  descricaoDoNegocio?: string | null;
  dataAbertura?: dayjs.Dayjs | null;
  urlContratoSocial?: string | null;
  capitalSocial?: number | null;
  cnae?: string | null;
  pessoaJuridica?: IPessoajuridica | null;
  tributacao?: ITributacao | null;
  ramo?: IRamo | null;
  enquadramento?: IEnquadramento | null;
}

export type NewEmpresa = Omit<IEmpresa, 'id'> & { id: null };
