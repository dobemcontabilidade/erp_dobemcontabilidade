import dayjs from 'dayjs/esm';
import { IParcelamentoImposto } from 'app/entities/parcelamento-imposto/parcelamento-imposto.model';
import { MesCompetenciaEnum } from 'app/entities/enumerations/mes-competencia-enum.model';

export interface IParcelaImpostoAPagar {
  id: number;
  numeroParcela?: number | null;
  dataVencimento?: dayjs.Dayjs | null;
  dataPagamento?: dayjs.Dayjs | null;
  valor?: number | null;
  valorMulta?: number | null;
  urlArquivoPagamento?: string | null;
  urlArquivoComprovante?: string | null;
  mesCompetencia?: keyof typeof MesCompetenciaEnum | null;
  parcelamentoImposto?: IParcelamentoImposto | null;
}

export type NewParcelaImpostoAPagar = Omit<IParcelaImpostoAPagar, 'id'> & { id: null };
