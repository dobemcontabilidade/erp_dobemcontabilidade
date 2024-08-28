import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';
import { TipoTelefoneEnum } from 'app/entities/enumerations/tipo-telefone-enum.model';

export interface ITelefone {
  id: number;
  codigoArea?: string | null;
  telefone?: string | null;
  principla?: boolean | null;
  tipoTelefone?: keyof typeof TipoTelefoneEnum | null;
  pessoa?: IPessoaFisica | null;
}

export type NewTelefone = Omit<ITelefone, 'id'> & { id: null };
