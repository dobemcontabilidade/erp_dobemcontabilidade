import dayjs from 'dayjs/esm';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';
import { ICertificadoDigital } from 'app/entities/certificado-digital/certificado-digital.model';
import { IFornecedorCertificado } from 'app/entities/fornecedor-certificado/fornecedor-certificado.model';

export interface ICertificadoDigitalEmpresa {
  id: number;
  urlCertificado?: string | null;
  dataContratacao?: dayjs.Dayjs | null;
  dataVencimento?: dayjs.Dayjs | null;
  diasUso?: number | null;
  pessoaJuridica?: IPessoajuridica | null;
  certificadoDigital?: ICertificadoDigital | null;
  fornecedorCertificado?: IFornecedorCertificado | null;
}

export type NewCertificadoDigitalEmpresa = Omit<ICertificadoDigitalEmpresa, 'id'> & { id: null };
