import { IContador } from 'app/entities/contador/contador.model';
import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';

export interface IAreaContabilContador {
  id: number;
  percentualExperiencia?: number | null;
  descricaoExperiencia?: string | null;
  ativo?: boolean | null;
  contador?: IContador | null;
  areaContabil?: IAreaContabil | null;
}

export type NewAreaContabilContador = Omit<IAreaContabilContador, 'id'> & { id: null };
