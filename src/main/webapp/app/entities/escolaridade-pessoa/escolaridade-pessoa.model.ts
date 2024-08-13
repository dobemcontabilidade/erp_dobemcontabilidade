import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IEscolaridade } from 'app/entities/escolaridade/escolaridade.model';

export interface IEscolaridadePessoa {
  id: number;
  nomeInstituicao?: string | null;
  anoConclusao?: number | null;
  urlComprovanteEscolaridade?: string | null;
  pessoa?: IPessoa | null;
  escolaridade?: IEscolaridade | null;
}

export type NewEscolaridadePessoa = Omit<IEscolaridadePessoa, 'id'> & { id: null };
