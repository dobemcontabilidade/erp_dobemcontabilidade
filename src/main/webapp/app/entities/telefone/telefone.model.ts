import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { TipoTelefoneEnum } from 'app/entities/enumerations/tipo-telefone-enum.model';

export interface ITelefone {
  id: number;
  codigoArea?: string | null;
  telefone?: string | null;
  principal?: boolean | null;
  tipoTelefone?: keyof typeof TipoTelefoneEnum | null;
  pessoa?: IPessoa | null;
}

export type NewTelefone = Omit<ITelefone, 'id'> & { id: null };
