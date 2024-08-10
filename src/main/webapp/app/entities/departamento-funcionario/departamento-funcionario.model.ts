import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { IDepartamento } from 'app/entities/departamento/departamento.model';

export interface IDepartamentoFuncionario {
  id: number;
  cargo?: string | null;
  funcionario?: Pick<IFuncionario, 'id' | 'nome'> | null;
  departamento?: Pick<IDepartamento, 'id' | 'nome'> | null;
}

export type NewDepartamentoFuncionario = Omit<IDepartamentoFuncionario, 'id'> & { id: null };
