import { IContador } from 'app/entities/contador/contador.model';
import { IBanco } from 'app/entities/banco/banco.model';

export interface IBancoContador {
  id: number;
  agencia?: string | null;
  conta?: string | null;
  digitoAgencia?: string | null;
  digitoConta?: string | null;
  principal?: boolean | null;
  contador?: Pick<IContador, 'id' | 'nome'> | null;
  banco?: Pick<IBanco, 'id' | 'nome'> | null;
}

export type NewBancoContador = Omit<IBancoContador, 'id'> & { id: null };
