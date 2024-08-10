import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { IPerfilContador } from 'app/entities/perfil-contador/perfil-contador.model';

export interface IPerfilContadorDepartamento {
  id: number;
  quantidadeEmpresas?: number | null;
  percentualExperiencia?: number | null;
  departamento?: Pick<IDepartamento, 'id' | 'nome'> | null;
  perfilContador?: Pick<IPerfilContador, 'id' | 'perfil'> | null;
}

export type NewPerfilContadorDepartamento = Omit<IPerfilContadorDepartamento, 'id'> & { id: null };
