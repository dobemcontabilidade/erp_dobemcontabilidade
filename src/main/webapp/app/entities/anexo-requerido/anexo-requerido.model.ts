import { TipoAnexoRequeridoEnum } from 'app/entities/enumerations/tipo-anexo-requerido-enum.model';

export interface IAnexoRequerido {
  id: number;
  nome?: string | null;
  tipo?: keyof typeof TipoAnexoRequeridoEnum | null;
  descricao?: string | null;
}

export type NewAnexoRequerido = Omit<IAnexoRequerido, 'id'> & { id: null };
