import dayjs from 'dayjs/esm';

export interface ITermoContratoContabil {
  id: number;
  titulo?: string | null;
  descricao?: string | null;
  urlDocumentoFonte?: string | null;
  documento?: string | null;
  disponivel?: boolean | null;
  dataCriacao?: dayjs.Dayjs | null;
}

export type NewTermoContratoContabil = Omit<ITermoContratoContabil, 'id'> & { id: null };
