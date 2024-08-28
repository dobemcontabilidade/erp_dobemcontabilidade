import { SituacaoPlanoContabilEnum } from 'app/entities/enumerations/situacao-plano-contabil-enum.model';

export interface IPlanoAssinaturaContabil {
  id: number;
  nome?: string | null;
  adicionalSocio?: number | null;
  adicionalFuncionario?: number | null;
  sociosIsentos?: number | null;
  adicionalFaturamento?: number | null;
  valorBaseFaturamento?: number | null;
  valorBaseAbertura?: number | null;
  situacao?: keyof typeof SituacaoPlanoContabilEnum | null;
}

export type NewPlanoAssinaturaContabil = Omit<IPlanoAssinaturaContabil, 'id'> & { id: null };
