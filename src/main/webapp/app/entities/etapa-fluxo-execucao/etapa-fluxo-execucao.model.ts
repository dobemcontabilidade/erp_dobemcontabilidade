import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';

export interface IEtapaFluxoExecucao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  feito?: boolean | null;
  ordem?: number | null;
  agendada?: boolean | null;
  ordemServico?: IOrdemServico | null;
}

export type NewEtapaFluxoExecucao = Omit<IEtapaFluxoExecucao, 'id'> & { id: null };
