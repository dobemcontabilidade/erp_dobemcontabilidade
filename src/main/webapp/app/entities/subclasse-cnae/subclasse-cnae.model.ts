import { IClasseCnae } from 'app/entities/classe-cnae/classe-cnae.model';
import { ISegmentoCnae } from 'app/entities/segmento-cnae/segmento-cnae.model';

export interface ISubclasseCnae {
  id: number;
  codigo?: string | null;
  descricao?: string | null;
  anexo?: number | null;
  atendidoFreemium?: boolean | null;
  atendido?: boolean | null;
  optanteSimples?: boolean | null;
  aceitaMEI?: boolean | null;
  categoria?: string | null;
  classe?: Pick<IClasseCnae, 'id' | 'descricao'> | null;
  segmentoCnaes?: Pick<ISegmentoCnae, 'id'>[] | null;
}

export type NewSubclasseCnae = Omit<ISubclasseCnae, 'id'> & { id: null };
