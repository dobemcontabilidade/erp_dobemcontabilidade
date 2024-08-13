import dayjs from 'dayjs/esm';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IContador } from 'app/entities/contador/contador.model';
import { IFluxoModelo } from 'app/entities/fluxo-modelo/fluxo-modelo.model';
import { StatusDaOSEnum } from 'app/entities/enumerations/status-da-os-enum.model';

export interface IOrdemServico {
  id: number;
  valor?: number | null;
  prazo?: dayjs.Dayjs | null;
  dataCriacao?: dayjs.Dayjs | null;
  dataHoraCancelamento?: dayjs.Dayjs | null;
  statusDaOS?: keyof typeof StatusDaOSEnum | null;
  descricao?: string | null;
  empresa?: IEmpresa | null;
  contador?: IContador | null;
  fluxoModelo?: IFluxoModelo | null;
}

export type NewOrdemServico = Omit<IOrdemServico, 'id'> & { id: null };
