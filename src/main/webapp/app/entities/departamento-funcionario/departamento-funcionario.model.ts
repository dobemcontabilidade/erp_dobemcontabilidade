import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { IDepartamento } from 'app/entities/departamento/departamento.model';

export interface IDepartamentoFuncionario {
  id: number;
  cargo?: string | null;
  funcionario?: IFuncionario | null;
  departamento?: IDepartamento | null;
}

export type NewDepartamentoFuncionario = Omit<IDepartamentoFuncionario, 'id'> & { id: null };
