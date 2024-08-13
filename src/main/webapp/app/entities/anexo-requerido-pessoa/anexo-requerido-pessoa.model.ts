import { IAnexoPessoa } from 'app/entities/anexo-pessoa/anexo-pessoa.model';
import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';
import { TipoAnexoPessoaEnum } from 'app/entities/enumerations/tipo-anexo-pessoa-enum.model';

export interface IAnexoRequeridoPessoa {
  id: number;
  obrigatorio?: boolean | null;
  tipo?: keyof typeof TipoAnexoPessoaEnum | null;
  anexoPessoa?: IAnexoPessoa | null;
  anexoRequerido?: IAnexoRequerido | null;
}

export type NewAnexoRequeridoPessoa = Omit<IAnexoRequeridoPessoa, 'id'> & { id: null };
