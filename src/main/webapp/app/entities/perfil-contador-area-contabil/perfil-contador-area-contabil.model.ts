import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';

export interface IPerfilContadorAreaContabil {
  id: number;
  quantidadeEmpresas?: number | null;
  percentualExperiencia?: number | null;
  areaContabil?: Pick<IAreaContabil, 'id' | 'nome'> | null;
  perfilContador?: Pick<IPerfilContador, 'id' | 'perfil'> | null;
}

export type NewPerfilContadorAreaContabil = Omit<IPerfilContadorAreaContabil, 'id'> & { id: null };
