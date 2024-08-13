import { IPessoa } from 'app/entities/pessoa/pessoa.model';

export interface IEmail {
  id: number;
  email?: string | null;
  principal?: boolean | null;
  pessoa?: IPessoa | null;
}

export type NewEmail = Omit<IEmail, 'id'> & { id: null };
