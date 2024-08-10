import dayjs from 'dayjs/esm';
import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';

export interface IEmpresa {
  id: number;
  razaoSocial?: string | null;
  nomeFantasia?: string | null;
  descricaoDoNegocio?: string | null;
  cnpj?: string | null;
  dataAbertura?: dayjs.Dayjs | null;
  urlContratoSocial?: string | null;
  capitalSocial?: number | null;
  segmentoCnaes?: Pick<ISegmentoCnae, 'id'>[] | null;
  ramo?: Pick<IRamo, 'id' | 'nome'> | null;
  tributacao?: Pick<ITributacao, 'id' | 'nome'> | null;
  enquadramento?: Pick<IEnquadramento, 'id' | 'nome'> | null;
}

export type NewEmpresa = Omit<IEmpresa, 'id'> & { id: null };
