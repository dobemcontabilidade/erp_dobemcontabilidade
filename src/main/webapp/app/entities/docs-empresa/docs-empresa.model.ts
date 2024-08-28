import dayjs from 'dayjs/esm';
import { IPessoajuridica } from 'app/entities/pessoajuridica/pessoajuridica.model';

export interface IDocsEmpresa {
  id: number;
  documento?: string | null;
  descricao?: string | null;
  url?: string | null;
  dataEmissao?: dayjs.Dayjs | null;
  dataEncerramento?: dayjs.Dayjs | null;
  orgaoEmissor?: string | null;
  pessoaJuridica?: IPessoajuridica | null;
}

export type NewDocsEmpresa = Omit<IDocsEmpresa, 'id'> & { id: null };
