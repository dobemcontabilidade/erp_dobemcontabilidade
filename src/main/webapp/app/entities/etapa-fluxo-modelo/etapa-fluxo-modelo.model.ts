import { IFluxoModelo } from 'app/entities/fluxo-modelo/fluxo-modelo.model';

export interface IEtapaFluxoModelo {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  ordem?: number | null;
  fluxoModelo?: IFluxoModelo | null;
}

export type NewEtapaFluxoModelo = Omit<IEtapaFluxoModelo, 'id'> & { id: null };
