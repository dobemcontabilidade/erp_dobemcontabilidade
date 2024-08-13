import { IServicoContabil } from 'app/entities/servico-contabil/servico-contabil.model';
import { IAnexoRequerido } from 'app/entities/anexo-requerido/anexo-requerido.model';

export interface IAnexoRequeridoServicoContabil {
  id: number;
  obrigatorio?: boolean | null;
  servicoContabil?: IServicoContabil | null;
  anexoRequerido?: IAnexoRequerido | null;
}

export type NewAnexoRequeridoServicoContabil = Omit<IAnexoRequeridoServicoContabil, 'id'> & { id: null };
