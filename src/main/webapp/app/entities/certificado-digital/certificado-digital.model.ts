import { TipoCertificadoEnum } from 'app/entities/enumerations/tipo-certificado-enum.model';

export interface ICertificadoDigital {
  id: number;
  nome?: string | null;
  sigla?: string | null;
  descricao?: string | null;
  tipoCertificado?: keyof typeof TipoCertificadoEnum | null;
}

export type NewCertificadoDigital = Omit<ICertificadoDigital, 'id'> & { id: null };
