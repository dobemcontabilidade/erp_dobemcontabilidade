import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { IEsfera } from 'app/entities/esfera/esfera.model';

export interface IServicoContabil {
  id: number;
  nome?: string | null;
  valor?: number | null;
  descricao?: string | null;
  diasExecucao?: number | null;
  geraMulta?: boolean | null;
  periodoExecucao?: number | null;
  diaLegal?: number | null;
  mesLegal?: number | null;
  valorRefMulta?: number | null;
  areaContabil?: IAreaContabil | null;
  esfera?: IEsfera | null;
}

export type NewServicoContabil = Omit<IServicoContabil, 'id'> & { id: null };
