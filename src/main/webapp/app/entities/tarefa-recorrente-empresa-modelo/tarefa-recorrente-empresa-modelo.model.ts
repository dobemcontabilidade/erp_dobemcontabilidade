import { IServicoContabilEmpresaModelo } from 'app/entities/servico-contabil-empresa-modelo/servico-contabil-empresa-modelo.model';
import { MesCompetenciaEnum } from 'app/entities/enumerations/mes-competencia-enum.model';
import { TipoRecorrenciaEnum } from 'app/entities/enumerations/tipo-recorrencia-enum.model';

export interface ITarefaRecorrenteEmpresaModelo {
  id: number;
  diaAdmin?: number | null;
  mesLegal?: keyof typeof MesCompetenciaEnum | null;
  recorrencia?: keyof typeof TipoRecorrenciaEnum | null;
  servicoContabilEmpresaModelo?: IServicoContabilEmpresaModelo | null;
}

export type NewTarefaRecorrenteEmpresaModelo = Omit<ITarefaRecorrenteEmpresaModelo, 'id'> & { id: null };
