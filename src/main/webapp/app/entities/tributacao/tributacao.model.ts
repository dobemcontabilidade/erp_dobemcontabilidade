import { SituacaoTributacaoEnum } from 'app/entities/enumerations/situacao-tributacao-enum.model';

export interface ITributacao {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  situacao?: keyof typeof SituacaoTributacaoEnum | null;
}

export type NewTributacao = Omit<ITributacao, 'id'> & { id: null };
