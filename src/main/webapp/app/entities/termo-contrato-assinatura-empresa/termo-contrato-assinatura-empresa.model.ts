import dayjs from 'dayjs/esm';
import { ITermoContratoContabil } from 'app/entities/termo-contrato-contabil/termo-contrato-contabil.model';
import { IAssinaturaEmpresa } from 'app/entities/assinatura-empresa/assinatura-empresa.model';
import { SituacaoTermoContratoAssinadoEnum } from 'app/entities/enumerations/situacao-termo-contrato-assinado-enum.model';

export interface ITermoContratoAssinaturaEmpresa {
  id: number;
  dataAssinatura?: dayjs.Dayjs | null;
  dataEnvioEmail?: dayjs.Dayjs | null;
  urlDocumentoAssinado?: string | null;
  situacao?: keyof typeof SituacaoTermoContratoAssinadoEnum | null;
  termoContratoContabil?: ITermoContratoContabil | null;
  empresa?: IAssinaturaEmpresa | null;
}

export type NewTermoContratoAssinaturaEmpresa = Omit<ITermoContratoAssinaturaEmpresa, 'id'> & { id: null };
