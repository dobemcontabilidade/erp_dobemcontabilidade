import dayjs from 'dayjs/esm';
import { IAreaContabil } from 'app/entities/area-contabil/area-contabil.model';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { IContador } from 'app/entities/contador/contador.model';

export interface IAreaContabilAssinaturaEmpresa {
  id: number;
  ativo?: boolean | null;
  dataAtribuicao?: dayjs.Dayjs | null;
  dataRevogacao?: dayjs.Dayjs | null;
  areaContabil?: IAreaContabil | null;
  assinaturaEmpresa?: IAssinaturaEmpresa | null;
  contador?: IContador | null;
}

export type NewAreaContabilAssinaturaEmpresa = Omit<IAreaContabilAssinaturaEmpresa, 'id'> & { id: null };
