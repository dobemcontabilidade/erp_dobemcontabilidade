import { IFuncionario } from 'app/entities/funcionario/funcionario.model';

export interface IEstrangeiro {
  id: number;
  dataChegada?: string | null;
  dataNaturalizacao?: string | null;
  casadoComBrasileiro?: boolean | null;
  filhosComBrasileiro?: boolean | null;
  checked?: boolean | null;
  funcionario?: IFuncionario | null;
}

export type NewEstrangeiro = Omit<IEstrangeiro, 'id'> & { id: null };
