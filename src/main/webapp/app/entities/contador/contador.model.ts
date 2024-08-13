import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';
import { SituacaoContadorEnum } from 'app/entities/enumerations/situacao-contador-enum.model';

export interface IContador {
  id: number;
  crc?: string | null;
  limiteEmpresas?: number | null;
  limiteDepartamentos?: number | null;
  limiteFaturamento?: number | null;
  situacaoContador?: keyof typeof SituacaoContadorEnum | null;
  pessoa?: IPessoa | null;
  usuarioContador?: IUsuarioContador | null;
  perfilContador?: IPerfilContador | null;
}

export type NewContador = Omit<IContador, 'id'> & { id: null };
