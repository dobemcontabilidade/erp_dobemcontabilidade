import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IBanco } from 'app/entities/banco/banco.model';
import { TipoContaBancoEnum } from 'app/entities/enumerations/tipo-conta-banco-enum.model';

export interface IBancoPessoa {
  id: number;
  agencia?: string | null;
  conta?: string | null;
  tipoConta?: keyof typeof TipoContaBancoEnum | null;
  principal?: boolean | null;
  pessoa?: IPessoa | null;
  banco?: IBanco | null;
}

export type NewBancoPessoa = Omit<IBancoPessoa, 'id'> & { id: null };
