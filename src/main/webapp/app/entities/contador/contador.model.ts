import { RacaECorEnum } from 'app/entities/enumerations/raca-e-cor-enum.model';
import { PessoaComDeficienciaEnum } from 'app/entities/enumerations/pessoa-com-deficiencia-enum.model';
import { EstadoCivilEnum } from 'app/entities/enumerations/estado-civil-enum.model';
import { SexoEnum } from 'app/entities/enumerations/sexo-enum.model';
import { SituacaoContadorEnum } from 'app/entities/enumerations/situacao-contador-enum.model';

export interface IContador {
  id: number;
  nome?: string | null;
  cpf?: string | null;
  dataNascimento?: string | null;
  tituloEleitor?: number | null;
  rg?: string | null;
  rgOrgaoExpeditor?: string | null;
  rgUfExpedicao?: string | null;
  nomeMae?: string | null;
  nomePai?: string | null;
  localNascimento?: string | null;
  racaECor?: keyof typeof RacaECorEnum | null;
  pessoaComDeficiencia?: keyof typeof PessoaComDeficienciaEnum | null;
  estadoCivil?: keyof typeof EstadoCivilEnum | null;
  sexo?: keyof typeof SexoEnum | null;
  urlFotoPerfil?: string | null;
  rgOrgaoExpditor?: string | null;
  crc?: string | null;
  limiteEmpresas?: number | null;
  limiteAreaContabils?: number | null;
  limiteFaturamento?: number | null;
  limiteDepartamentos?: number | null;
  situacaoContador?: keyof typeof SituacaoContadorEnum | null;
}

export type NewContador = Omit<IContador, 'id'> & { id: null };
