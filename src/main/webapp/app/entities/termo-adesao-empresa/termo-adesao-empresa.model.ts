import dayjs from 'dayjs/esm';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IPlanoContabil } from 'app/entities/plano-contabil/plano-contabil.model';

export interface ITermoAdesaoEmpresa {
  id: number;
  dataAdesao?: dayjs.Dayjs | null;
  checked?: boolean | null;
  urlDoc?: string | null;
  empresa?: IEmpresa | null;
  planoContabil?: IPlanoContabil | null;
}

export type NewTermoAdesaoEmpresa = Omit<ITermoAdesaoEmpresa, 'id'> & { id: null };
