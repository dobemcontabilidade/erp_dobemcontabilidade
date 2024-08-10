import dayjs from 'dayjs/esm';
import { IContador } from 'app/entities/contador/contador.model';
import { ITermoDeAdesao } from 'app/entities/termo-de-adesao/termo-de-adesao.model';

export interface ITermoAdesaoContador {
  id: number;
  dataAdesao?: dayjs.Dayjs | null;
  contador?: Pick<IContador, 'id' | 'nome'> | null;
  termoDeAdesao?: Pick<ITermoDeAdesao, 'id' | 'titulo'> | null;
}

export type NewTermoAdesaoContador = Omit<ITermoAdesaoContador, 'id'> & { id: null };
