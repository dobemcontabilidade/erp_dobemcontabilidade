import dayjs from 'dayjs/esm';
import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';

export interface IEmpresa {
  id: number;
  razaoSocial?: string | null;
  nomeFantasia?: string | null;
  descricaoDoNegocio?: string | null;
  cnpj?: string | null;
  dataAbertura?: dayjs.Dayjs | null;
  urlContratoSocial?: string | null;
  capitalSocial?: number | null;
  segmentoCnaes?: ISegmentoCnae[] | null;
  empresaModelo?: IEmpresaModelo | null;
}

export type NewEmpresa = Omit<IEmpresa, 'id'> & { id: null };
