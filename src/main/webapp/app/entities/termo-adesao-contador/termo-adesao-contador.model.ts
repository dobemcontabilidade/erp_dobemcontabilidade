import dayjs from 'dayjs/esm';
import { IContador } from 'app/entities/contador/contador.model';
import { ITermoDeAdesao } from 'app/entities/termo-de-adesao/termo-de-adesao.model';

export interface ITermoAdesaoContador {
  id: number;
  dataAdesao?: dayjs.Dayjs | null;
  checked?: boolean | null;
  urlDoc?: string | null;
  contador?: IContador | null;
  termoDeAdesao?: ITermoDeAdesao | null;
}

export type NewTermoAdesaoContador = Omit<ITermoAdesaoContador, 'id'> & { id: null };
