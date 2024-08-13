import { IModulo } from 'app/entities/modulo/modulo.model';

export interface IFuncionalidade {
  id: number;
  nome?: string | null;
  ativa?: boolean | null;
  modulo?: IModulo | null;
}

export type NewFuncionalidade = Omit<IFuncionalidade, 'id'> & { id: null };
