import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';

export interface IPerfilContadorDepartamento {
  id: number;
  quantidadeEmpresas?: number | null;
  percentualExperiencia?: number | null;
  departamento?: IDepartamento | null;
  perfilContador?: IPerfilContador | null;
}

export type NewPerfilContadorDepartamento = Omit<IPerfilContadorDepartamento, 'id'> & { id: null };
