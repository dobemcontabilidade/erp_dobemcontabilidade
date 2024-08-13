import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { IEnquadramento } from 'app/entities/enquadramento/enquadramento.model';
import { ITributacao } from 'app/entities/tributacao/tributacao.model';
import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IEmpresaModelo {
  id: number;
  nome?: string | null;
  observacao?: string | null;
  segmentoCnaes?: ISegmentoCnae[] | null;
  ramo?: IRamo | null;
  enquadramento?: IEnquadramento | null;
  tributacao?: ITributacao | null;
  cidade?: ICidade | null;
}

export type NewEmpresaModelo = Omit<IEmpresaModelo, 'id'> & { id: null };
