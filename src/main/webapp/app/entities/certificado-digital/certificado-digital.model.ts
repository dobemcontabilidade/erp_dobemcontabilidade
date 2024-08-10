import dayjs from 'dayjs/esm';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { TipoCertificadoEnum } from 'app/entities/enumerations/tipo-certificado-enum.model';

export interface ICertificadoDigital {
  id: number;
  urlCertificado?: string | null;
  dataContratacao?: dayjs.Dayjs | null;
  validade?: number | null;
  tipoCertificado?: keyof typeof TipoCertificadoEnum | null;
  empresa?: Pick<IEmpresa, 'id' | 'razaoSocial'> | null;
}

export type NewCertificadoDigital = Omit<ICertificadoDigital, 'id'> & { id: null };
