import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { IContador } from 'app/entities/contador/contador.model';

export interface IAreaContabilContador {
  id: number;
  percentualExperiencia?: number | null;
  descricaoExperiencia?: string | null;
  pontuacaoEntrevista?: number | null;
  pontuacaoAvaliacao?: number | null;
  areaContabil?: Pick<IAreaContabil, 'id' | 'nome'> | null;
  contador?: Pick<IContador, 'id' | 'nome'> | null;
}

export type NewAreaContabilContador = Omit<IAreaContabilContador, 'id'> & { id: null };
