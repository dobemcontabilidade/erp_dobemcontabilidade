import dayjs from 'dayjs/esm';
import { EstadoCivilEnum } from 'app/entities/enumerations/estado-civil-enum.model';
import { SexoEnum } from 'app/entities/enumerations/sexo-enum.model';

export interface IPessoa {
  id: number;
  nome?: string | null;
  cpf?: string | null;
  dataNascimento?: dayjs.Dayjs | null;
  tituloEleitor?: string | null;
  rg?: string | null;
  rgOrgaoExpditor?: string | null;
  rgUfExpedicao?: string | null;
  estadoCivil?: keyof typeof EstadoCivilEnum | null;
  sexo?: keyof typeof SexoEnum | null;
  urlFotoPerfil?: string | null;
}

export type NewPessoa = Omit<IPessoa, 'id'> & { id: null };
