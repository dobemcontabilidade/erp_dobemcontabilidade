import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';

export interface IContador {
  id: number;
  nome?: string | null;
  crc?: string | null;
  limiteEmpresas?: number | null;
  limiteAreaContabils?: number | null;
  limiteFaturamento?: number | null;
  limiteDepartamentos?: number | null;
  pessoa?: Pick<IPessoa, 'id' | 'nome'> | null;
  perfilContador?: Pick<IPerfilContador, 'id' | 'perfil'> | null;
}

export type NewContador = Omit<IContador, 'id'> & { id: null };
