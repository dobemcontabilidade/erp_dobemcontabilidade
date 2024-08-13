import { IPessoa } from 'app/entities/pessoa/pessoa.model';
import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { TipoDependenteFuncionarioEnum } from 'app/entities/enumerations/tipo-dependente-funcionario-enum.model';

export interface IDependentesFuncionario {
  id: number;
  urlCertidaoDependente?: string | null;
  urlRgDependente?: string | null;
  dependenteIRRF?: boolean | null;
  dependenteSalarioFamilia?: boolean | null;
  tipoDependenteFuncionarioEnum?: keyof typeof TipoDependenteFuncionarioEnum | null;
  pessoa?: IPessoa | null;
  funcionario?: IFuncionario | null;
}

export type NewDependentesFuncionario = Omit<IDependentesFuncionario, 'id'> & { id: null };
