import { ICidade } from 'app/entities/cidade/cidade.model';

export interface IFluxoModelo {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  cidade?: ICidade | null;
}

export type NewFluxoModelo = Omit<IFluxoModelo, 'id'> & { id: null };
