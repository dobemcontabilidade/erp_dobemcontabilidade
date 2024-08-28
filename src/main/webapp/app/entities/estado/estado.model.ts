import { IPais } from 'app/entities/pais/pais.model';

export interface IEstado {
  id: number;
  nome?: string | null;
  naturalidade?: string | null;
  sigla?: string | null;
  pais?: IPais | null;
}

export type NewEstado = Omit<IEstado, 'id'> & { id: null };
