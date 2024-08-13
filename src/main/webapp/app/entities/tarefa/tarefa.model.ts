import dayjs from 'dayjs/esm';
import { IEsfera } from 'app/entities/esfera/esfera.model';
import { IFrequencia } from 'app/entities/frequencia/frequencia.model';
import { ICompetencia } from 'app/entities/competencia/competencia.model';
import { TipoTarefaEnum } from 'app/entities/enumerations/tipo-tarefa-enum.model';

export interface ITarefa {
  id: number;
  titulo?: string | null;
  numeroDias?: number | null;
  diaUtil?: boolean | null;
  valor?: number | null;
  notificarCliente?: boolean | null;
  geraMulta?: boolean | null;
  exibirEmpresa?: boolean | null;
  dataLegal?: dayjs.Dayjs | null;
  pontos?: number | null;
  tipoTarefa?: keyof typeof TipoTarefaEnum | null;
  esfera?: IEsfera | null;
  frequencia?: IFrequencia | null;
  competencia?: ICompetencia | null;
}

export type NewTarefa = Omit<ITarefa, 'id'> & { id: null };
