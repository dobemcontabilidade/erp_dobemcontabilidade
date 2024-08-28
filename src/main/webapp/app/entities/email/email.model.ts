import { IPessoaFisica } from 'app/entities/pessoa-fisica/pessoa-fisica.model';

export interface IEmail {
  id: number;
  email?: string | null;
  principal?: boolean | null;
  pessoa?: IPessoaFisica | null;
}

export type NewEmail = Omit<IEmail, 'id'> & { id: null };
