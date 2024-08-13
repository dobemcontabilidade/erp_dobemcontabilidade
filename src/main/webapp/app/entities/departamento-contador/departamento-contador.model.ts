import { IDepartamento } from 'app/entities/departamento/departamento.model';
import { IContador } from 'app/entities/contador/contador.model';

export interface IDepartamentoContador {
  id: number;
  percentualExperiencia?: number | null;
  descricaoExperiencia?: string | null;
  pontuacaoEntrevista?: number | null;
  pontuacaoAvaliacao?: number | null;
  departamento?: IDepartamento | null;
  contador?: IContador | null;
}

export type NewDepartamentoContador = Omit<IDepartamentoContador, 'id'> & { id: null };
